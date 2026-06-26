package br.com.developed.ideal.atendimento.api.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.developed.ideal.atendimento.flowable.service.FlowableService;
import br.com.developed.ideal.atendimento.utils.AppUtils;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/workflow", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class WorkflowController {
	
	private final FlowableService flowableService;
	
	@DeleteMapping("/cancelar-processo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelar(@RequestParam String businessKey, @RequestParam String reason) {
		
		flowableService.cancelProcessInstanceByBusinessKey(businessKey, reason);
		
	}
	
	@GetMapping("/teste")
	public String teste(@RequestParam String tipo) {
		return AppUtils.getExtensaoArquivoPeloMimeType(tipo);
	}

}
