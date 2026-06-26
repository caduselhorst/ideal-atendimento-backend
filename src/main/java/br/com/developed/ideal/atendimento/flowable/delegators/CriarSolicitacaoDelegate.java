package br.com.developed.ideal.atendimento.flowable.delegators;

import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.developed.ideal.atendimento.api.model.input.AnexosInput;
import br.com.developed.ideal.atendimento.api.model.input.ChamadoInput;
import br.com.developed.ideal.atendimento.domain.model.Chamado;
import br.com.developed.ideal.atendimento.domain.model.Cliente;
import br.com.developed.ideal.atendimento.domain.model.MensagemBp;
import br.com.developed.ideal.atendimento.domain.model.TipoMensagemBP;
import br.com.developed.ideal.atendimento.domain.service.ChamadoService;
import br.com.developed.ideal.atendimento.domain.service.MensagemBpService;
import br.com.developed.ideal.atendimento.domain.service.WppConnectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CriarSolicitacaoDelegate implements JavaDelegate {
	
	private final ObjectMapper objectMapper;
	private final ChamadoService chamadoService;
	private final MensagemBpService mensagemBpService;
	private final WppConnectService wppConnectService;
	
	
	@Override
	public void execute(DelegateExecution execution) {
		
		try {
			log.debug("Iniciando CriarSolicitacaoDelegate");
			
			String jsonAnexos = (String) execution.getVariable("anexos");
			String jsonCliente = (String) execution.getVariable("cliente");
			String assunto = (String) execution.getVariable("assunto");
			String detalhe = (String) execution.getVariable("detalhe");
			String fone = (String) execution.getVariable("fone");
			
			AnexosInput anexos = null;
			Cliente cliente = objectMapper.readValue(jsonCliente, Cliente.class);
			
			if (jsonAnexos != null) {
				anexos = objectMapper.readValue(jsonAnexos, AnexosInput.class);
			}
			
			ChamadoInput input = ChamadoInput.builder()
					.assunto(assunto)
					.clienteId(cliente.getId())
					.descricao(detalhe)
					.foneAbertura(fone)
					.build();
			
			
			Chamado chamado = chamadoService.criar(input, anexos);
			
			MensagemBp mensagem = mensagemBpService.buscarOuFalharPorChave("sendMsgChamadoCriado");
			
			Map<String, Object> vars = Map.of(
						"phone", fone,
						"nroChamado", chamado.getId()
					
					);
			
			String payload = StringSubstitutor.replace(mensagem.getPayload(), vars);
			
			wppConnectService.sendMessage(payload, TipoMensagemBP.TEXTO);
			
			
			log.debug("CriarSolicitacaoDelegate finalizado.");
		} catch (JsonProcessingException e) {
			log.error("Ocorreu um erro ao desserializar o objeto", e);
			throw new RuntimeException("Ocorreu erro ao desserializar o objeto", e);
		}
		
	}
	
	

}
