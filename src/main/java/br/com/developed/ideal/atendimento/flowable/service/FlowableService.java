package br.com.developed.ideal.atendimento.flowable.service;

import java.util.List;
import java.util.Map;

import org.flowable.engine.FormService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.form.FormData;
import org.flowable.engine.form.FormProperty;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlowableService {
	
	private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final FormService formService;
	
	public ProcessInstance iniciar(String contactId, Map<String, Object> variables, String processDefinitionKey) {

        try {
        	    
            return runtimeService.startProcessInstanceByKey(processDefinitionKey, contactId, variables);

        } catch (Exception e) {

            throw new RuntimeException("Ocorreu um erro ao iniciar o processo", e);

        }

    }
	
	public Task getTarefaAtual(String contactId) {

        Task task = taskService.createTaskQuery()
            .processInstanceBusinessKey(contactId)
            .active()
            .singleResult();

        return task;

    }
	
	public void encerrarTask(String taskId, Map<String, Object> variables) {

        taskService.complete(taskId, variables);

    }
	
	public List<FormProperty> getFormProperties(String taskId) {

        FormData formData = formService.getTaskFormData(taskId);

        return formData.getFormProperties();
    }
	
	public void cancelProcessInstance(String processInstanceId, String reason) {
        runtimeService.deleteProcessInstance(processInstanceId, reason);
    }
	
	public void cancelProcessInstanceByBusinessKey(String businessKey, String reason) {
        List<ProcessInstance> instances =
            runtimeService.createProcessInstanceQuery()
                .processInstanceBusinessKey(businessKey)
                .list();
        
        ProcessInstance root = instances.stream()
            .filter(pi -> pi.getSuperExecutionId() == null)
            .findFirst()
            .orElse(null);
        
        if(root == null) {
        	log.info("Não existem instâncias de processos em andamento para a business key [{}]", businessKey);
        	return;
        }

        cancelProcessInstance(root.getId(), reason);
    }

}
