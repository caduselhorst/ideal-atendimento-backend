package br.com.developed.ideal.atendimento.domain.comunication.listener;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.developed.ideal.atendimento.domain.comunication.event.WebHookEvent;
import br.com.developed.ideal.atendimento.domain.model.webhook.WebHookModel;
import br.com.developed.ideal.atendimento.domain.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebHookListener {
	
	private final ObjectMapper mapper;
	
	private final WorkflowService workflowService;
	
	@Async
	@EventListener
	public void processar(WebHookEvent event) {
		
		log.debug("Processando novo evento de webhook!");
		
		
		try {
			
			JsonNode json = mapper.readTree(event.payload());
			
			String evento = json.get("event").asText();
			String body = json.get("body") != null ? json.get("body").asText() : null;
			String type = json.get("type") != null ? json.get("type").asText() : null;
			String from = json.get("from") != null ? json.get("from").asText() : null;
			String contentType = json.get("mimetype") == null ? null : json.get("mimetype").asText();
			String fileName = json.get("filename") == null ? null : json.get("filename").asText();
			String formattedNumber = json.get("sender") != null ? json.get("sender").get("formattedName").asText() : null;
			String listResponse = null;
			if (json.get("listResponse") != null) {
				listResponse =  json.get("listResponse").get("singleSelectReply").get("selectedRowId").asText();
			}
			
			String mensagem = 
					"""
					
					Evento: {}, 
					Body: {}, 
					Type: {}, 
					From: {},
					Content-Type: {},
					File name: {}
					Formatted number: {},
					List response: {}
					""";
			
			log.debug(
					mensagem, 
					evento, 
					body == null ? null : body.length() < 10 ? body : body.substring(0, 10) + "...[truncated]", 
					type,
					from,
					contentType,
					fileName,
					formattedNumber,
					listResponse
					);
			
			if (evento.equals("onmessage")) {
				workflowService.processaWebHook(
						new WebHookModel(
								evento, body, type, from, contentType, fileName, formattedNumber, listResponse));
			} else {
				log.debug("Eventos do tipo {} são desconsiderados", evento);
			}
			
				
			
			
		} catch (JsonProcessingException e) {
			
			log.error("Ocorreu um erro ao processar o paylod do evento", e);
			
		}
		
		
	}

}
