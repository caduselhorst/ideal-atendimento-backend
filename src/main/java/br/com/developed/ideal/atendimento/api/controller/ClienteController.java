package br.com.developed.ideal.atendimento.api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.developed.ideal.atendimento.api.assembler.ClienteModelAssembler;
import br.com.developed.ideal.atendimento.api.model.ClienteModel;
import br.com.developed.ideal.atendimento.core.security.CheckSecurity;
import br.com.developed.ideal.atendimento.domain.model.Cliente;
import br.com.developed.ideal.atendimento.domain.service.ClienteService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ClienteController {
	
	private final ClienteService clienteService;
	
	private final ClienteModelAssembler clienteModelAssembler;
	
	@CheckSecurity.Autenticado
	@GetMapping("/buscar")
	public ClienteModel buscarPorDocumento(@RequestParam(required = true) String documento) {
		
		Cliente cliente = clienteService.buscarPorDocumentoESalvar(documento);
				
		return clienteModelAssembler.toModel(cliente);
		
	}

}
