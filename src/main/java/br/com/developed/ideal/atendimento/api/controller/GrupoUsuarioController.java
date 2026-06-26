package br.com.developed.ideal.atendimento.api.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.developed.ideal.atendimento.api.assembler.GrupoUsuarioModelAssembler;
import br.com.developed.ideal.atendimento.api.assembler.UsuarioModelAssembler;
import br.com.developed.ideal.atendimento.api.model.GrupoUsuarioModel;
import br.com.developed.ideal.atendimento.api.model.UsuarioModel;
import br.com.developed.ideal.atendimento.api.model.input.GrupoUsuarioInput;
import br.com.developed.ideal.atendimento.core.security.CheckSecurity;
import br.com.developed.ideal.atendimento.domain.model.GrupoUsuario;
import br.com.developed.ideal.atendimento.domain.model.Usuario;
import br.com.developed.ideal.atendimento.domain.repository.GrupoUsuarioRepository;
import br.com.developed.ideal.atendimento.domain.service.GrupoUsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/grupos-usuario", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class GrupoUsuarioController {

	private GrupoUsuarioRepository grupoUsuarioRepository;
	private GrupoUsuarioService grupoUsuarioService;
	private GrupoUsuarioModelAssembler grupoUsuarioModelAssembler;
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@CheckSecurity.GruposUsuario.PermiteConsultar
	@GetMapping
	public Page<GrupoUsuarioModel> paginar(
			@RequestParam(required = true) String filtro, 
			Pageable pageable) {
		
		return grupoUsuarioModelAssembler
				.toPageModel(grupoUsuarioRepository
						.findByDataExclusaoIsNullAndDescricaoContainsIgnoreCase(filtro, pageable));
		
	}
	
	@CheckSecurity.GruposUsuario.PermiteConsultar
	@GetMapping("/listar")
	public List<GrupoUsuarioModel> listar(@RequestParam(required = true) String filtro) {
		
		return grupoUsuarioModelAssembler
				.toListModel(grupoUsuarioRepository
						.findByDataExclusaoIsNullAndDescricaoContainsIgnoreCase(filtro));
		
	}
	
	@CheckSecurity.GruposUsuario.PermiteConsultar
	@GetMapping("/{grupoUsuarioId}")
	public GrupoUsuarioModel buscar(@PathVariable Long grupoUsuarioId) {
		
		return grupoUsuarioModelAssembler
				.toModel(grupoUsuarioService.buscarOuFalhar(grupoUsuarioId));
		
	}
	
	@CheckSecurity.GruposUsuario.PermiteConsultar
	@GetMapping("/{grupoUsuarioId}/usuarios")
	public List<UsuarioModel> usuarios(@PathVariable Long grupoUsuarioId) {
		
		GrupoUsuario grupo = grupoUsuarioService.buscarOuFalhar(grupoUsuarioId);
		
		List<Usuario> usuarios = grupo.getUsuarios();
		
		return usuarioModelAssembler.toListModel(usuarios);
		
	}
	
	@CheckSecurity.GruposUsuario.PermiteCriar
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public GrupoUsuarioModel criar (@Valid @RequestBody GrupoUsuarioInput input) {
		
		return grupoUsuarioModelAssembler
				.toModel(grupoUsuarioService
						.gravar(input));
		
	}
	
	@CheckSecurity.GruposUsuario.PermiteAlterar
	@PutMapping("/{grupoUsuarioId}")
	public GrupoUsuarioModel alterar (@PathVariable Long grupoUsuarioId, @Valid @RequestBody GrupoUsuarioInput input) {
		
		return grupoUsuarioModelAssembler
				.toModel(grupoUsuarioService
						.alterar(grupoUsuarioId, input));
		
	}
	
	@CheckSecurity.GruposUsuario.PermiteAlterar
	@PutMapping("/{grupoUsuarioId}/usuarios/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> adicionarUsusario (
			@PathVariable(name = "grupoUsuarioId") Long grupoUsuarioId, 
			@PathVariable(name = "usuarioId") Long usuarioId) {
		
		grupoUsuarioService.adicionaUsuarioEmGrupo(grupoUsuarioId, usuarioId);
		
		return ResponseEntity.noContent().build();
		
	}
	
	@CheckSecurity.GruposUsuario.PermiteAlterar
	@DeleteMapping("/{grupoUsuarioId}/usuarios/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> removerUsuario (
			@PathVariable Long grupoUsuarioId, @PathVariable Long usuarioId) {
		
		grupoUsuarioService.removeUsuarioDeGrupo(grupoUsuarioId, usuarioId);
		
		return ResponseEntity.noContent().build();
		
	}
	
	@CheckSecurity.GruposUsuario.PermiteExcluir
	@DeleteMapping("/{grupoUsuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> excluir (@PathVariable Long grupoUsuarioId) {
		
		grupoUsuarioService.excluir(grupoUsuarioId);
		
		return ResponseEntity.noContent().build();
		
	}
	
}
