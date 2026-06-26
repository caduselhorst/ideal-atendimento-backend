package br.com.developed.ideal.atendimento.domain.service;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import br.com.developed.ideal.atendimento.domain.exception.CepMalFormadoException;
import br.com.developed.ideal.atendimento.domain.exception.CepNaoEncontradoException;
import br.com.developed.ideal.atendimento.domain.exception.ErroExternoException;
import br.com.developed.ideal.atendimento.domain.exception.ParametroLocalidadeInvalidoException;
import br.com.developed.ideal.atendimento.domain.model.EnderecoCorreio;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConsultaEnderecoService {

	private RestTemplate restTemplate;

	public EnderecoCorreio consultaEnderecoPeloCep(String cep) {

		try {
			URI resourceURI = URI.create("https://viacep.com.br/ws/" + cep + "/json");

			EnderecoCorreio endereco = restTemplate.getForObject(resourceURI, EnderecoCorreio.class);
			
			if(endereco.getErro() != null && endereco.getErro().booleanValue()) {
				throw new CepNaoEncontradoException("O cep informado não foi encontrado");
			}
			
			return endereco;
		} catch (RestClientResponseException e) {
						
			if(e.getStatusCode().value() == 400) {
				throw new CepMalFormadoException("O cep informado não está bem formado. Aceita somente números e comprimento de 8");
			} else {
				throw new RuntimeException("Ocorreu um erro ao consultar o cep", e);
			}
		}

	}
	
	public List<EnderecoCorreio> consultaEnderecosPorLocalidade(String uf, String cidade, String logradouro) {
		try {
						
			String url = String.format("https://viacep.com.br/ws/%1s/%2s/%3s/json", 
					uf, formataValorParametro(cidade), formataValorParametro(logradouro));
									
			URI resourceUri = URI.create(url);

			EnderecoCorreio[] enderecos = restTemplate.getForObject(resourceUri, EnderecoCorreio[].class);
			
			return Arrays.asList(enderecos);
		} catch (RestClientResponseException e) {
			
			if(e.getStatusCode().value() == 400) {
				throw new ParametroLocalidadeInvalidoException("Parâmetro de localidade inválido");
			} else {
				throw new ErroExternoException("Ocorreu um erro ao consultar o cep", e);
			}
		} 
	}
	
	private String formataValorParametro(String parametro) {
		
		String novo = parametro.replace(" ", "%20");
		
		return novo.toLowerCase()
				.replace("ã", "a")
				.replace("õ", "o")
				.replace("á", "a")
				.replace("é", "e")
				.replace("ç", "c")
				.replace("ó", "o")
				.replace("ê", "e")
				.replace("â", "a");
		
	}

}
