package br.com.developed.ideal.atendimento.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.developed.ideal.atendimento.api.assembler.PerfilModelAssembler;
import br.com.developed.ideal.atendimento.api.assembler.PermissaoModelAssembler;
import br.com.developed.ideal.atendimento.api.model.PerfilModel;
import br.com.developed.ideal.atendimento.api.model.PerfilResumoModel;
import br.com.developed.ideal.atendimento.api.model.PermissaoModel;
import br.com.developed.ideal.atendimento.api.model.input.PerfilInput;
import br.com.developed.ideal.atendimento.core.security.CheckSecurity;
import br.com.developed.ideal.atendimento.domain.model.Perfil;
import br.com.developed.ideal.atendimento.domain.repository.PerfilRepository;
import br.com.developed.ideal.atendimento.domain.service.PerfilService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/perfis", produces = MediaType.APPLICATION_JSON_VALUE)
public class PerfilController {
	
	private PerfilRepository perfilRepository;
	
	private PerfilService perfilService;
	
	private PerfilModelAssembler perfilModelAssembler;
	
	private PermissaoModelAssembler permissaoModelAssembler;
	
	@CheckSecurity.Perfis.PermiteConsultar
	@GetMapping
	public List<PerfilResumoModel> lista() {
		
		return perfilModelAssembler
				.toListResumoModel(perfilRepository.findAll());
		
	}
	
	@CheckSecurity.Perfis.PermiteConsultar
	@GetMapping("/{perfilId}")
	public PerfilModel busca(@PathVariable Long perfilId) {
		
		return perfilModelAssembler
				.toModel(perfilService.buscarOuFalhar(perfilId));
		
	}
	
	@CheckSecurity.Perfis.PermiteConsultar
	@GetMapping("/{perfilId}/permissoes")
	public List<PermissaoModel> listaPermissoes(@PathVariable Long perfilId) {
		
		Perfil perfil = perfilService.buscarOuFalhar(perfilId);
		
		return permissaoModelAssembler.toListModel(perfil.getPermissoes());
		
	}
	
	@CheckSecurity.Perfis.PermiteCriar
	@PostMapping
	public PerfilModel cria(@Valid @RequestBody PerfilInput input) {
		
		return perfilModelAssembler
				.toModel(perfilService.grava(input));
		
	}
	
	@CheckSecurity.Perfis.PermiteAlterar
	@PutMapping("/{perfilId}")
	public PerfilModel altera(
			@PathVariable Long perfilId, 
			@Valid @RequestBody PerfilInput input) {
		
		return perfilModelAssembler
				.toModel(perfilService.altera(perfilId, input));
		
	}
	
	@CheckSecurity.Perfis.PermiteAlterar
	@PutMapping("/{perfilId}/permissoes/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associaPermissao(@PathVariable Long perfilId, @PathVariable Long permissaoId) {
		
		perfilService.associaPermissao(perfilId, permissaoId);
		
		return ResponseEntity.noContent().build();
		
	}
	
	@CheckSecurity.Perfis.PermiteAlterar
	@DeleteMapping("/{perfilId}/permissoes/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociaPermissao(@PathVariable Long perfilId, @PathVariable Long permissaoId) {
		
		perfilService.desassociaPermissao(perfilId, permissaoId);
		
		return ResponseEntity.noContent().build();
		
	}

}
