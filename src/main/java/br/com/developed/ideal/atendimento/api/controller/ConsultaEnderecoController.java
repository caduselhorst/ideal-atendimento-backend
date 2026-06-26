package br.com.developed.ideal.atendimento.api.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.developed.ideal.atendimento.core.security.CheckSecurity;
import br.com.developed.ideal.atendimento.core.validation.Estado;
import br.com.developed.ideal.atendimento.domain.model.EnderecoCorreio;
import br.com.developed.ideal.atendimento.domain.service.ConsultaEnderecoService;
import lombok.AllArgsConstructor;


@Validated
@RestController
@RequestMapping(path = "enderecos", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ConsultaEnderecoController {
	
	private ConsultaEnderecoService consultaEnderecoService;
	

	@CheckSecurity.Autenticado
	@GetMapping(path = "/cep")
	public ResponseEntity<EnderecoCorreio> porCep(@RequestParam(required = true) String cep) {
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(24, TimeUnit.HOURS))
				.body(consultaEnderecoService.consultaEnderecoPeloCep(cep));
	}
	

	@CheckSecurity.Autenticado
	@GetMapping(path = "/localidade")
	public ResponseEntity<List<EnderecoCorreio>> porLocalidade(
			@RequestParam(required = true) @Estado String uf, 
			@RequestParam(required = true) String cidade, 
			@RequestParam(required = true) String logradouro) {
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(24, TimeUnit.HOURS))
				.body(consultaEnderecoService.consultaEnderecosPorLocalidade(uf, cidade, logradouro));
		
	}

}
