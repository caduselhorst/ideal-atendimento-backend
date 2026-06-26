package br.com.developed.ideal.atendimento.domain.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.developed.ideal.atendimento.core.properties.WppConnectServerProperties;
import br.com.developed.ideal.atendimento.domain.model.TipoMensagemBP;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class WppConnectService {
	
	private final RestTemplate restTemplate;
	private final WppConnectServerProperties wppConnectServerProperties;
	
	public void sendMessage(String payload, TipoMensagemBP tipo) {
		
		try {
			
			String url = wppConnectServerProperties.getBaseUrl();
			
			if (tipo.equals(TipoMensagemBP.TEXTO)) {
				url = url + "/send-message";
			} else {
				url = url + "/send-list-message";
			}
			
			HttpEntity<String> request = new HttpEntity<>(payload, getHeaders());
			
			restTemplate.postForEntity(url, request, String.class);
			
			
		} catch (HttpClientErrorException e) {
			
			log.error("Ocorreu um erro de Cliente", e);
			
		} catch (HttpServerErrorException e) {
			
			log.error("Ocorreu um erro de Servidor", e);
			
		}
		
	}
		
	private HttpHeaders getHeaders() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + wppConnectServerProperties.getToken());
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		return headers;
		
	}

}
