package br.com.developed.ideal.atendimento.domain.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.developed.ideal.atendimento.core.properties.ApplicationProperties;
import br.com.developed.ideal.atendimento.domain.exception.NegocioException;
import br.com.developed.ideal.atendimento.domain.model.maxdata.ClienteApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MaxdataService {
	
	private RestTemplate restTemplate;
	private ApplicationProperties applicationProperties;
	
	public ClienteApi findClientMaxdataByCpfCgc(String cpfCgc) {
		
		try {
			
			String url = applicationProperties.getBaseUrl() + "/clientes?cpfCgc=" + cpfCgc;
			
			ResponseEntity<ClienteApi[]> response = restTemplate.getForEntity(url, ClienteApi[].class);
			
			ClienteApi[] clientes = response.getBody();
			
			if (clientes.length > 0) {
				return clientes[0];
			} else {
				return null;
			}
			
		} catch (HttpClientErrorException e) {
			throw new NegocioException("Ocorreu um erro 4xx o buscar o cliente no Maxdata");
		} catch (HttpServerErrorException e) {
			throw new RuntimeException("Ocorreu um erro 5xx o buscar o cliente no Maxdata");
		}
		
	}

}
