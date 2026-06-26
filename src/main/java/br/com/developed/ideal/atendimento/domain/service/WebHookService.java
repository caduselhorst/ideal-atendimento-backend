package br.com.developed.ideal.atendimento.domain.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.com.developed.ideal.atendimento.domain.comunication.event.WebHookEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebHookService {
	
	private final ApplicationEventPublisher publisher;
	
	public void processaEventoWebHook(String payload) {
		
		publisher.publishEvent(new WebHookEvent(payload));
		
	}

}
