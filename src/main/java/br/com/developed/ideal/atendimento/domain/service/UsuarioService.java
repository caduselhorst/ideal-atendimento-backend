package br.com.developed.ideal.atendimento.domain.service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.developed.ideal.atendimento.api.assembler.UsuarioInputDisassembler;
import br.com.developed.ideal.atendimento.api.model.ResetSenhaModel;
import br.com.developed.ideal.atendimento.api.model.input.UsuarioInput;
import br.com.developed.ideal.atendimento.domain.exception.GrupoUsuarioNaoEncontradoException;
import br.com.developed.ideal.atendimento.domain.exception.NegocioException;
import br.com.developed.ideal.atendimento.domain.exception.ResetSenhaNaoEncontradoException;
import br.com.developed.ideal.atendimento.domain.exception.UsuarioNaoEncontradoException;
import br.com.developed.ideal.atendimento.domain.model.Constantes;
import br.com.developed.ideal.atendimento.domain.model.GrupoUsuario;
import br.com.developed.ideal.atendimento.domain.model.Perfil;
import br.com.developed.ideal.atendimento.domain.model.ResetSenha;
import br.com.developed.ideal.atendimento.domain.model.Usuario;
import br.com.developed.ideal.atendimento.domain.repository.GrupoUsuarioRepository;
import br.com.developed.ideal.atendimento.domain.repository.ResetSenhaRepository;
import br.com.developed.ideal.atendimento.domain.repository.UsuarioRepository;
import br.com.developed.ideal.atendimento.utils.AppUtils;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UsuarioService {
	
	private UsuarioRepository usuarioRepository;
	private ResetSenhaRepository resetSenhaRepository;
	private GrupoUsuarioRepository grupoUsuarioRepository;

	
	private UsuarioInputDisassembler usuarioInputDisassembler;
	
	private PasswordEncoder passwordEncoder;
	
	private PerfilService perfilService;
	
	
	public Usuario getUsuarioLogado() {
		
		var auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getName().equals("anonymousUser")) {
			return null;
		}
		
		Usuario usuario = usuarioRepository.findByLoginIgnoreCase(auth.getName()).get();
		return usuario;
		
	}
	
	public Usuario buscarOuFalhar(Long usuarioId) {
		
		return usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
		
	}
	
	public List<Usuario> getUsuariosPerfilAdministrador() {
		
		List<Usuario>  usuarios = usuarioRepository.findAll();
		
		Perfil administrador = perfilService.buscarOuFalhar(1L);
		
		return usuarios
				.stream()
				.filter(e -> e.getPerfis().contains(administrador))
				.toList();
		
	}
	
	

	@Transactional
	public Usuario grava(UsuarioInput input) {
		
		if(!usuarioRepository.existsByLoginIgnoreCase(input.getLogin())) {
			
			
			try {
			
				Usuario usuario = usuarioInputDisassembler.toDomainModel(input);
				
				String senha = AppUtils.generateRandomString(6);
				
				usuario.setLogin(usuario.getLogin().toLowerCase());
				
				usuario.setSenha(passwordEncoder.encode(senha));
				
				usuario = usuarioRepository.save(usuario);
				
				Perfil perfilPadrao = perfilService.carregaPerfilPadrao();
				
				usuario.associaPerfil(perfilPadrao);
				
				usuario = usuarioRepository.save(usuario);
				
				
				return usuario;
				
			} catch (DataIntegrityViolationException e) {
				
				throw new NegocioException("E-mail ou documento já informado");
				
			}
			
		} else {
			
			throw new NegocioException("Já existe no cadastro um usuário com o login informado");
			
		}
		
		
	}
	
	@Transactional
	public Usuario altera (Long usuarioId, UsuarioInput input ) {
		
		try {
			
			Usuario usuario = buscarOuFalhar(usuarioId);
			
			Optional<Usuario>  optUsuario = usuarioRepository.findByLoginIgnoreCase(input.getLogin());
			
			if (!optUsuario.isEmpty() && !optUsuario.get().getId().equals(usuario.getId())) {
				
				throw new NegocioException("Já existe no cadastro um usuário com o login informado");
				
			}
			
			usuarioInputDisassembler.toDomainModel(input, usuario);
					
			usuario = usuarioRepository.save(usuario);
			usuarioRepository.flush();
			
			return usuario;
			
		} catch (DataIntegrityViolationException e) {
			
			throw new NegocioException("Código de liberação já informado");
			
		}
		
	}
	
	@Transactional
	public void ativar(Long usuarioId) {
		
		Usuario usuario = buscarOuFalhar(usuarioId);
		
		usuario.ativar();
		
	}
	
	@Transactional
	public void inativar(Long usuarioId) {
		
		Usuario usuario = buscarOuFalhar(usuarioId);
		
		usuario.inativar();
		
	}
	
	@Transactional
	public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha, String confirmacaoNovaSenha) {
		
		if(!novaSenha.equals(confirmacaoNovaSenha)) {
			throw new NegocioException("Foram passadas valores diferentes para nova nenha e confirmação da nova senha");
		}
		
		Usuario usuario = buscarOuFalhar(usuarioId);
		
		if(!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
			throw new NegocioException("A senha atual não bate com a senha gravada");
		}
		
		usuario.alteraSenha(passwordEncoder.encode(novaSenha));
		
	}
	
	@Transactional
	public void associaPerfil(Long usuarioId, Long perfilId) {
		
		Usuario usuario = buscarOuFalhar(usuarioId);
		Perfil perfil = perfilService.buscarOuFalhar(perfilId);
		
		usuario.associaPerfil(perfil);
		
	}
	
	@Transactional
	public void desassociaPerfil(Long usuarioId, Long perfilId) {
		
		Usuario usuario = buscarOuFalhar(usuarioId);
		Perfil perfil = perfilService.buscarOuFalhar(perfilId);
		
		usuario.desassociaPerfil(perfil);
		
	}
	
	
	
	
	@Transactional
	public void adicionaGrupo(Long usuarioId, Long grupoUsuarioId) {
		
		Usuario usuario = buscarOuFalhar(usuarioId);
		GrupoUsuario grupoUsuario = grupoUsuarioRepository.findById(grupoUsuarioId)
				.orElseThrow(() -> new GrupoUsuarioNaoEncontradoException(grupoUsuarioId));
		
		usuario.adicionaGrupo(grupoUsuario);
		
	}
	
	@Transactional
	public void removeGrupo(Long usuarioId, Long grupoUsuarioId) {
		
		Usuario usuario = buscarOuFalhar(usuarioId);
		GrupoUsuario grupoUsuario = grupoUsuarioRepository.findById(grupoUsuarioId)
				.orElseThrow(() -> new GrupoUsuarioNaoEncontradoException(grupoUsuarioId));
		
		usuario.removeGrupo(grupoUsuario);
		
	}
	
	

	@Transactional
	public void resetaSenha(String id, String codigo) {
		
		ResetSenha resetSenha = resetSenhaRepository.findByIdAndExecutado(id, false)
				.orElseThrow(() -> new ResetSenhaNaoEncontradoException(id));
		
		OffsetDateTime agora = OffsetDateTime.now();
		
		if (agora.compareTo(resetSenha.getDataValidade()) < 0) {
			
			Usuario usuario = buscarOuFalhar(resetSenha.getUsuario().getId());
			
			String novaSenha = AppUtils.generateRandomString(6);
			
			usuario.setSenha(passwordEncoder.encode(novaSenha));
			
			resetSenha.setExecutado(true);

			usuario = usuarioRepository.save(usuario);
			resetSenha = resetSenhaRepository.save(resetSenha);
			
			
		} else {
			throw new NegocioException("Código de reset de senha expirado. Reinicie o processo de redefinição de senha");
		}
				
		
	}
	
	
	
	@Transactional
	public void redefinirSenha(Long usuarioId) {
		
		Usuario usuario = buscarOuFalhar(usuarioId);
		
		usuario.alteraSenha(passwordEncoder.encode(Constantes.SENHA_PADRAO));
		
	}
	
	@Transactional
	public ResetSenhaModel geraId(String email) {
		
		
		Optional<Usuario> optUsuario = usuarioRepository.findByLoginIgnoreCase(email);
		
		if (optUsuario.isPresent()) {
			
			Usuario usuario = optUsuario.get();
			
				
				String id = AppUtils.toMD5(usuario.getLogin() + "-" + usuario.getDocumento() + OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
				
				String codigo = String.valueOf(AppUtils.generateRandomSequence());
				
				ResetSenha resetSenha = ResetSenha.builder()
						.codigoAutorizacao(codigo)
						.dataValidade(OffsetDateTime.now().plusMinutes(30L))
						.id(id)
						.usuario(usuario)
						.executado(false)
						.build();
				
				
				resetSenha = resetSenhaRepository.save(resetSenha);
				
				
				return ResetSenhaModel
						.builder()
						.id(id)
						.build();
				
				

			
		} else {
			return ResetSenhaModel
					.builder()
					.id(null)
					.build();
		}
		
		
	}
	
	

}
