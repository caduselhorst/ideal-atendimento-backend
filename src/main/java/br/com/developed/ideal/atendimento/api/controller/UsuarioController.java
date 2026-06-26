package br.com.developed.ideal.atendimento.api.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import br.com.developed.ideal.atendimento.api.assembler.PerfilModelAssembler;
import br.com.developed.ideal.atendimento.api.assembler.UsuarioModelAssembler;
import br.com.developed.ideal.atendimento.api.model.GrupoUsuarioModel;
import br.com.developed.ideal.atendimento.api.model.PerfilResumoModel;
import br.com.developed.ideal.atendimento.api.model.UsuarioModel;
import br.com.developed.ideal.atendimento.api.model.input.AlterarSenhaInput;
import br.com.developed.ideal.atendimento.api.model.input.UsuarioInput;
import br.com.developed.ideal.atendimento.core.security.CheckSecurity.Autenticado;
import br.com.developed.ideal.atendimento.core.security.CheckSecurity.Usuarios;
import br.com.developed.ideal.atendimento.domain.model.Usuario;
import br.com.developed.ideal.atendimento.domain.repository.UsuarioRepository;
import br.com.developed.ideal.atendimento.domain.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController {
	
	private UsuarioRepository usuarioRepository;
	
	private UsuarioService usuarioService;
	
	private UsuarioModelAssembler usuarioModelAssembler;
	
	private PerfilModelAssembler perfilModelAssembler;
	
	private GrupoUsuarioModelAssembler grupoUsuarioModelAssembler;
	
	
	@Usuarios.PermiteConsultar
	@GetMapping
	public Page<UsuarioModel> paginar(
			@PageableDefault(size = 10) Pageable pageable, 
			@RequestParam(defaultValue = "") String nome) {
		
		return usuarioModelAssembler.toPageModel(usuarioRepository.findByNomeContainingIgnoreCase(pageable, nome));
		
	}
	
	@Usuarios.PermiteConsultar
	@GetMapping("/listar")
	public List<UsuarioModel> listar() {
		
		return usuarioModelAssembler.toListModel(usuarioRepository.findByInativoOrderByNome(false));
		
	}
	
	
	
	@Usuarios.PermiteConsultar
	@GetMapping("/{usuarioId}")
	public UsuarioModel buscar(@PathVariable Long usuarioId) {
		
		return usuarioModelAssembler.toModel(usuarioService.buscarOuFalhar(usuarioId));
		
	}
	

	
	@Usuarios.PermiteConsultar
	@GetMapping("/{usuarioId}/perfis")
	public List<PerfilResumoModel> listaPerfis(@PathVariable Long usuarioId) {
		
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		
		return perfilModelAssembler.toListResumoModel(usuario.getPerfis());
		
	}
	
	@Usuarios.PermiteAlterar
	@GetMapping("/{usuarioId}/grupos")
	public List<GrupoUsuarioModel> listaGrupos(@PathVariable Long usuarioId) {
		
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		
		return grupoUsuarioModelAssembler.toListModel(usuario.getGrupos());
		
	}
	
	
	@Usuarios.PermiteCriar
	@PostMapping
	public UsuarioModel grava(@Valid @RequestBody UsuarioInput input) {
		
		return usuarioModelAssembler.toModel(usuarioService.grava(input));
		
	}
	
	@Usuarios.PermiteAlterar
	@PutMapping("/{usuarioId}")
	public UsuarioModel altera(@PathVariable Long usuarioId, @Valid @RequestBody UsuarioInput input) {
		
		return usuarioModelAssembler.toModel(usuarioService.altera(usuarioId, input));
		
	}
	
	@Usuarios.PermiteAlterar
	@PutMapping("/{usuarioId}/ativar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativa(@PathVariable Long usuarioId) {
		
		usuarioService.ativar(usuarioId);
		
		return ResponseEntity.noContent().build();
		
	}
	
	@Usuarios.PermiteAlterar
	@PutMapping("/{usuarioId}/inativar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> inativa(@PathVariable Long usuarioId) {
		
		usuarioService.inativar(usuarioId);
		
		return ResponseEntity.noContent().build();
		
	}
	
	@Usuarios.PermiteAlterar
	@PutMapping("/{usuarioId}/perfis/{perfilId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associaPerfil(@PathVariable Long usuarioId, @PathVariable Long perfilId) {
		usuarioService.associaPerfil(usuarioId, perfilId);
		return ResponseEntity.noContent().build();
	}
	
	@Usuarios.PermiteAlterar
	@DeleteMapping("/{usuarioId}/perfis/{perfilId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociaPerfil(@PathVariable Long usuarioId, @PathVariable Long perfilId) {
		usuarioService.desassociaPerfil(usuarioId, perfilId);
		return ResponseEntity.noContent().build();
	}
	
	@Usuarios.PermiteAlterar
	@PutMapping("/{usuarioId}/grupos/{grupoUsuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> adicionaEmGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoUsuarioId) {
		
		usuarioService.adicionaGrupo(usuarioId, grupoUsuarioId);
		
		return ResponseEntity.noContent().build();
	}
	
	@Usuarios.PermiteAlterar
	@DeleteMapping("/{usuarioId}/grupos/{grupoUsuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> removeGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoUsuarioId) {
		
		usuarioService.removeGrupo(usuarioId, grupoUsuarioId);
		
		return ResponseEntity.noContent().build();
	}
	
	
	@Usuarios.PermiteAlterar
	@PutMapping("/{usuarioId}/senha/redefinir")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> redefinir(@PathVariable Long usuarioId) {
		usuarioService.redefinirSenha(usuarioId);
		return ResponseEntity.noContent().build();
	}
	
	@Autenticado
	@PutMapping("/{usuarioId}/senha")
	public ResponseEntity<Void>  alterarSenha (@PathVariable Long usuarioId,  @Valid @RequestBody AlterarSenhaInput input ) {
		
		usuarioService.alterarSenha(usuarioId, input.getSenhaAtual(), 
				input.getNovaSenha(), input.getConfirmacaoNovaSenha());
		
		return ResponseEntity.noContent().build();
	}

}
