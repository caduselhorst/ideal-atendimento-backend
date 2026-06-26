package br.com.developed.ideal.atendimento.flowable.delegators;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.StringSubstitutor;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.developed.ideal.atendimento.domain.exception.ChamadoNaoEncontradoException;
import br.com.developed.ideal.atendimento.domain.model.Chamado;
import br.com.developed.ideal.atendimento.domain.model.Cliente;
import br.com.developed.ideal.atendimento.domain.model.MensagemBp;
import br.com.developed.ideal.atendimento.domain.model.StatusChamado;
import br.com.developed.ideal.atendimento.domain.service.ChamadoService;
import br.com.developed.ideal.atendimento.domain.service.MensagemBpService;
import br.com.developed.ideal.atendimento.domain.service.WppConnectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConsultarInformarSolicitacaoDelegate implements JavaDelegate {
	
	private final ChamadoService chamadoService;
	private final MensagemBpService mensagemBpService;
	private final ObjectMapper objectMapper;
	private final WppConnectService wppConnectService;
	
	
	private static final String MSG_CHAMADO_NAO_ENCONTRADO = "sendMsgChamadoNaoEncontrado";
	private static final String MSG_CHAMADO_ENCONTRADO = "sendMsgChamadoEncontrado";
	
	@Override
	public void execute(DelegateExecution execution) {
		
		
		String clienteJson = (String) execution.getVariable("cliente");
		String nroSolicitacao = (String) execution.getVariable("nroSolicitacao");
		String telefone = (String) execution.getVariable("fone");
		
		
		if (clienteJson == null) {
			throw new RuntimeException("Cliente não configurado no workflow");
		}
		
		if (nroSolicitacao == null) {
			throw new RuntimeException("Número da solicitação não configurado no workflow");
		}
		
		try {
			
			Cliente cliente = objectMapper.readValue(clienteJson, Cliente.class);
			
			Long chamadoId = Long.parseLong(nroSolicitacao);
			
			Chamado chamado  = chamadoService.buscarPorIdECnpj(chamadoId, cliente.getDocumento());
			
			MensagemBp mensagem = mensagemBpService.buscarOuFalharPorChave(MSG_CHAMADO_ENCONTRADO);
			
			Long id = chamado.getId();
			
			String dataAbertura = chamado.getDataAbertura()
					.atZoneSameInstant(ZoneId.of("-03:00"))
					.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
			
			String status = getStatus(chamado.getStatusChamado());
			
			String analista = chamado.getUsuarioTratamento() == null ? "" : chamado.getUsuarioTratamento().getNome();
			
			String dataEncerramento = chamado.getDataFechamento() == null ? "" : chamado.getDataFechamento()
					.atZoneSameInstant(ZoneId.of("-03:00"))
					.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
			
			String descricao = chamado.getDescricaoFechamento() == null ? "" :
				chamado.getDescricaoFechamento();
			
			Map<String, Object> vars = Map.of(
				"phone", telefone,
				"chamadoId", id,
				"dataAbertura", dataAbertura,
				"status", status,
				"analista", analista ,
				"dataEncerramento", dataEncerramento,
				"descricaoEncerramento", StringEscapeUtils.escapeJava(descricao)
			);
			
			String payload = substituiVariaveis(vars, mensagem.getPayload());
			
			
			execution.setVariable("valorInvalido", false);
			
			wppConnectService.sendMessage(payload, mensagem.getTipo());
			
		} catch (JsonProcessingException e) {
			log.error("Ocorreu um erro ao desserializar o cliente", e);
		} catch (NumberFormatException e) {
			execution.setVariable("valorInvalido", true);
			log.error("Informado um valor inválido");
		} catch (ChamadoNaoEncontradoException e) {
			
			MensagemBp mensagem = mensagemBpService.buscarOuFalharPorChave(MSG_CHAMADO_NAO_ENCONTRADO);
			
			Map<String, Object> vars = Map.of(
				"phone", telefone
			);
			
			String payload = substituiVariaveis(vars, mensagem.getPayload());
			
			wppConnectService.sendMessage(payload, mensagem.getTipo());
			
		}
		
		
	}
	
	private String substituiVariaveis(Map<String, Object> vars, String mensagem) {
		
		return StringSubstitutor.replace(mensagem, vars);
		
	}
	
	private String getStatus(StatusChamado status) {
		
		return switch (status) {
			case AGUARDANDO -> 
				"Aguardando atendimento";
			case CANCELADO -> 
				"Cancelado";
			case EM_ATENDIMENTO -> 
				"Em atendimento";
			default -> 
				"Finalizado"; 
		}; 
		
	}
	
	

}
