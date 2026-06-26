package br.com.developed.ideal.atendimento.core.resttemplate;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import br.com.developed.ideal.atendimento.core.interceptor.LoggingInterceptor;

@Configuration
public class RestClientConfig {

	@Bean
	RestTemplate getRestTemplate(LoggingInterceptor loggingInterceptor) {
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setInterceptors(Collections.singletonList(loggingInterceptor));
		
		return restTemplate;
	}
	
}
