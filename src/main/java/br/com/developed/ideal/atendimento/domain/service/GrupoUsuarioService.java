package br.com.developed.ideal.atendimento.domain.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.developed.ideal.atendimento.api.assembler.GrupoUsuarioInputDisassembler;
import br.com.developed.ideal.atendimento.api.model.input.GrupoUsuarioInput;
import br.com.developed.ideal.atendimento.domain.exception.GrupoUsuarioNaoEncontradoException;
import br.com.developed.ideal.atendimento.domain.model.GrupoUsuario;
import br.com.developed.ideal.atendimento.domain.model.Usuario;
import br.com.developed.ideal.atendimento.domain.repository.GrupoUsuarioRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GrupoUsuarioService {
	
	private GrupoUsuarioRepository grupoUsuarioRepository;
	
	private GrupoUsuarioInputDisassembler grupoUsuarioInputDisassembler;
	
	private UsuarioService usuarioService;
	
	
	public GrupoUsuario buscarOuFalhar(Long grupoUsuarioId) {
		
		return grupoUsuarioRepository.findById(grupoUsuarioId)
				.orElseThrow(() -> new GrupoUsuarioNaoEncontradoException(grupoUsuarioId));
		
	}
	
	public GrupoUsuario buscarPorIdentificadorBpmsOuFalhar(String identificadorBpms) {
		
		return grupoUsuarioRepository.findByIdentificadorbpms(identificadorBpms)
				.orElseThrow(() -> new GrupoUsuarioNaoEncontradoException(identificadorBpms));
		
	}
	
	@Transactional
	public GrupoUsuario gravar (GrupoUsuarioInput input) {
		
		GrupoUsuario grupoUsuario = grupoUsuarioInputDisassembler.toDomainModel(input);
		Usuario usuarioResponsavel = usuarioService.buscarOuFalhar(input.getUsuarioResponsavelId());
		
		grupoUsuario.setDataInclusao(OffsetDateTime.now());
		grupoUsuario.setUsuarioInclusao(usuarioService.getUsuarioLogado());
		grupoUsuario.setUsuarioResponsavel(usuarioResponsavel);
		
		
		grupoUsuario = grupoUsuarioRepository.save(grupoUsuario);
		
		return grupoUsuario;
		
	}
	
	@Transactional
	public GrupoUsuario alterar (Long grupoUsuarioId, GrupoUsuarioInput input) {
		
		GrupoUsuario grupoUsuario = buscarOuFalhar(grupoUsuarioId);
		Usuario usuarioResponsavel = usuarioService.buscarOuFalhar(input.getUsuarioResponsavelId());
		
		grupoUsuarioInputDisassembler.toDomainModel(input, grupoUsuario);
		
		grupoUsuario.setDataAlteracao(OffsetDateTime.now());
		grupoUsuario.setUsuarioAlteracao(usuarioService.getUsuarioLogado());
		grupoUsuario.setUsuarioResponsavel(usuarioResponsavel);
		
		grupoUsuario = grupoUsuarioRepository.save(grupoUsuario);
		
		
		return grupoUsuario;
	}
	
	
	@Transactional
	public void excluir(Long grupoUsuarioId) {
		
		GrupoUsuario grupoUsuario = buscarOuFalhar(grupoUsuarioId);
		
		if (grupoUsuario.getDataExclusao() == null) {			
			grupoUsuario.setDataExclusao(OffsetDateTime.now());
			grupoUsuario.setUsuarioExclusao(usuarioService.getUsuarioLogado());
		}
		
	}
	
	@Transactional
	public void adicionaUsuarioEmGrupo(Long grupoUsuarioId, Long usuarioId) {
		
		GrupoUsuario grupoUsuario = buscarOuFalhar(grupoUsuarioId);
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		
		grupoUsuario.adicionaUsuario(usuario);
		
	}
	
	@Transactional
	public void removeUsuarioDeGrupo(Long grupoUsuarioId, Long usuarioId) {
		
		GrupoUsuario grupoUsuario = buscarOuFalhar(grupoUsuarioId);
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		
		grupoUsuario.removeUsuario(usuario);
		
	}

}
