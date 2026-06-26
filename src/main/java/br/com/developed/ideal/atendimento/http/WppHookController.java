package br.com.developed.ideal.atendimento.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.developed.ideal.atendimento.domain.service.WebHookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/httpwebhook")
@RequiredArgsConstructor
@Slf4j
public class WppHookController {
	
	private final WebHookService webHookService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<Void> receiveData(@RequestBody String body) {
		
		webHookService.processaEventoWebHook(body);
		
		return ResponseEntity.accepted().build();
		
	}

}
