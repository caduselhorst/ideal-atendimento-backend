package br.com.developed.ideal.atendimento.flowable.delegators;

import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.developed.ideal.atendimento.domain.model.Cliente;
import br.com.developed.ideal.atendimento.domain.model.MensagemBp;
import br.com.developed.ideal.atendimento.domain.service.MensagemBpService;
import br.com.developed.ideal.atendimento.domain.service.WppConnectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class EnviarMensagemDelegate implements JavaDelegate {
	
	private final MensagemBpService mensagemBpService;
	private final WppConnectService wppConnectService;
	private final ObjectMapper mapper;
	
	@Override
	public void execute(DelegateExecution execution) {
		

		try {
			
			FlowElement element = execution.getCurrentFlowElement();
			
			String chave = element.getDocumentation().trim();
			String fone = (String) execution.getVariable("fone");
			
			String jsonCliente = (String) execution.getVariable("cliente");
			Cliente cliente = jsonCliente == null ? null : mapper.readValue(jsonCliente, Cliente.class);
			
			String razao = cliente != null ? cliente.getRazao() : "";
			
			Map<String, Object> vars = Map.of(
					"phone", fone,
					"razaoSocial", razao
					);
			
			MensagemBp mensagem = mensagemBpService.buscarOuFalharPorChave(chave);
			
			wppConnectService.sendMessage(substituiVariaveis(mensagem.getPayload(), vars), mensagem.getTipo());
			
			log.debug("EnviarMensagemDelegate finalizou");
		
		} catch (JsonProcessingException e) {
			
			log.error("Ocorreu um erro ao desserializar um objeto", e);
			throw new RuntimeException("Ocorreu um erro ao desserializar um objeto", e);
			
		}
		
	}
	
	private String substituiVariaveis(String payload, Map<String, Object> vars) {
		
		String mensagem = StringSubstitutor.replace(payload, vars);
		
		return mensagem;
		
	}

}
