package br.com.developed.ideal.atendimento.core.resttemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClientConfig {

	@Bean
	RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
}
