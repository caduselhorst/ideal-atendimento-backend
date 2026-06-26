package br.com.developed.ideal.atendimento.api.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.developed.ideal.atendimento.api.assembler.PermissaoModelAssembler;
import br.com.developed.ideal.atendimento.api.model.PermissaoModel;
import br.com.developed.ideal.atendimento.core.security.CheckSecurity.Permissoes;
import br.com.developed.ideal.atendimento.domain.repository.PermissaoRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController {
	
	private PermissaoRepository permissaoRepository;
	
	private PermissaoModelAssembler permissaoModelAssembler;

	@Permissoes.PodeConsultar
	@GetMapping
	public List<PermissaoModel> lista() {
		
		return permissaoModelAssembler
				.toListModel(permissaoRepository.findAll());
		
	}
	
}
