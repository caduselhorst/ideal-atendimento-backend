package br.com.developed.ideal.atendimento.domain.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.engine.form.FormProperty;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.developed.ideal.atendimento.api.model.input.AnexoInput;
import br.com.developed.ideal.atendimento.api.model.input.AnexosInput;
import br.com.developed.ideal.atendimento.domain.model.webhook.WebHookModel;
import br.com.developed.ideal.atendimento.flowable.service.FlowableService;
import br.com.developed.ideal.atendimento.utils.AppUtils;
import br.com.developed.ideal.atendimento.utils.FoneUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkflowService {
	
	private final FlowableService flowableService;
	private final ObjectMapper objectMapper;
	
	private static final String PROCESS_DEFINITION_KEY = "idealSolicitacaoSuporteMaxData";
	
	public void processaWebHook(WebHookModel hookModel) {
		
		log.debug("Processando nova interação");
		
		String businessKey = hookModel.from();
		
		String fone = FoneUtils.normalizaFromHook(hookModel.formattedNumber());
		
		log.debug("Buscando tarefas ativas para {}", businessKey);
		Task tarefa = flowableService.getTarefaAtual(businessKey);
		
		if (tarefa == null) {
			
			log.debug ("Sem tarefas ativas. Será criado uma nova instância");
			Map<String, Object> variaveis = new HashMap<>();
			variaveis.put("fone", fone);
			
			ProcessInstance pi = flowableService.iniciar(businessKey, variaveis, PROCESS_DEFINITION_KEY);
			
			log.debug("Iniciada nova instância para: BusinessKey: {} Process instance id: {}", businessKey, pi.getId());
			
		} else {
			
			log.debug("Processo em andamento (Tarefa {}). Verificado tipo de tarefa, sentando propriedades e finalizando", tarefa.getName());
			
			/*
			 * Variável local para guardar o que o usuário digitou
			 */
			String resposta = hookModel.listResponse() != null ? 
					hookModel.listResponse() : hookModel.body().trim();
			
			String tipoResposta = hookModel.type();
			String nomeDocumento = hookModel.fileName();
			
			/*
			 * Seta a ultima mensagem informada pelo usuário nas variáveis da instância do processo
			 */
			Map<String, Object> variables = new HashMap<>();
            variables.put("resposta", resposta);
            variables.put("tipoResposta", tipoResposta);
            variables.put("nomeDocumento", nomeDocumento);
			
			List<FormProperty> propriedades = flowableService.getFormProperties(tarefa.getId());
			
			for (FormProperty propriedade : propriedades) {
				
				/*
				 * Iteração nas propriedades de formulários definidas para a user task no BPMN
				 */
				 
				 if (propriedade.getType().getName().equals("enum")) {
					 
					 /*
					  * Trata quando a propriedade de formulario setada na user task do flowable for do tipo enum.
					  * No caso da aplicaçõa, nos menus, quando é diposnibilizada uma lista para o usuário respoder
					  * Objetivo é tratar opções válidas e inválidas
					  */
					 
					 @SuppressWarnings("unchecked")
                     Map<String, String> valores = 
                         (Map<String, String>) propriedade.getType().getInformation("values");
					 
					 if (valores.containsKey(resposta)) {
						 
						 // a resposta do usuário existe nas propriedades da tarefa
						 // ou seja, é um valor válido
						 
						 variables.put(propriedade.getId(), resposta);
                         variables.put("opcaoInvalida", false);
						 
					 } else {
						 
						 // opção inválida
						 
						 log.warn("O valor {} não é válido para a propriedade {}", resposta, propriedade.getName());
                         variables.put(propriedade.getId(), "-1");
                         variables.put("opcaoInvalida", true);
						 
						 
					 }
					 
				 }
				 
				 if (propriedade.getType().getName().equals("string")) {
					 
					 /*
					  * trata quando a propriedade do formulário for do tipo string
					  */
					 
					 if (propriedade.getId().equals("arquivo")) {
						 
                         variables.put("nomeArquivo", hookModel.fileName());
                         variables.put("tipoArquivo", hookModel.contentType());
                         variables.put("conteudoArquivo", hookModel.body());
                         
                     } else {
                    	 
                    	 if(propriedade.getId().equals("detalhe")) {
                    		 
                    		 if (hookModel.type().equals("chat")) {
                    			 variables.put("detalhe", resposta);
                    		 } else {
                    			 
                    			 try {
                    			 variables.put("detalhe", "Enviado como anexo");
                    			 
	                    			 String nomeArquivo = hookModel.fileName() != null ? hookModel.fileName() : 
	    								 (LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + AppUtils.getExtensaoArquivoPeloMimeType(hookModel.contentType()));
	                    			 
	                    			 AnexosInput anexosInput = AnexosInput.builder()
	                    					 .anexos(Arrays.asList(AnexoInput.builder()
	                    							 .conteudoArquivo(resposta)
	                    							 .nomeArquivo(nomeArquivo)
	                    							 .tipoArquivo(hookModel.contentType())
	                    							 .build()))
	                    					 .build();
	                    			 
	                    			 variables.put("anexos", objectMapper.writeValueAsString(anexosInput));
                    			 } catch (JsonProcessingException e) {
                    				 log.error("Erro ao serializar o objeto de anexos", e);
                    				 throw new RuntimeException(e);
                    			 }

                    		 }
                    		 
                    	 } else {
                    		 
                    		 variables.put(propriedade.getId(), resposta);
                    	 }
                    	 
                         
                     }
				 }
				 
			}
			
			flowableService.encerrarTask(tarefa.getId(), variables);
			
		}
		
	}

}
