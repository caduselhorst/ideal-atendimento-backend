package br.com.developed.ideal.atendimento.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.developed.ideal.atendimento.api.assembler.PerfilInputDisassembler;
import br.com.developed.ideal.atendimento.api.model.input.PerfilInput;
import br.com.developed.ideal.atendimento.domain.exception.NegocioException;
import br.com.developed.ideal.atendimento.domain.exception.PerfilNaoEncontradoException;
import br.com.developed.ideal.atendimento.domain.model.Perfil;
import br.com.developed.ideal.atendimento.domain.model.Permissao;
import br.com.developed.ideal.atendimento.domain.repository.PerfilRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PerfilService {
	
	private PerfilRepository perfilRepository;
	
	private PerfilInputDisassembler perfilInputDisassembler;
	
	private PermissaoService permissaoService;
	
	@Transactional
	private void resetaPadrao() {
		
		List<Perfil> perfis = perfilRepository.findAll();
		
		perfis.forEach(p -> {
			Perfil aux = perfilRepository.findById(p.getId()).get();
			
			aux.setPadrao(false);
			
			aux = perfilRepository.save(aux);
		});
		
	}
	
	public Perfil buscarOuFalhar(Long perfilId) {
		
		return perfilRepository.findById(perfilId)
				.orElseThrow(() -> new PerfilNaoEncontradoException(perfilId));
		
	}
	
	public Perfil carregaPerfilPadrao() {
		
		List<Perfil> perfis = perfilRepository.findAll().stream().filter(p -> p.getPadrao()).toList();
		
		if (perfis.isEmpty()) {
			throw new NegocioException("Não existe um perfil configurado como padrão");
		}
		
		return perfis.get(0);
		
	}
	
	@Transactional
	public Perfil grava(PerfilInput input) {
				
		if (input.getPadrao()) {
			resetaPadrao();
		}
		
		Perfil perfil = perfilInputDisassembler.toDomainModel(input);
		return perfilRepository.save(perfil);
		
	}
	
	@Transactional
	public Perfil altera(Long perfilId, PerfilInput input) {
		
		if (input.getPadrao()) {
			resetaPadrao();
		}
		
		
		Perfil perfil = buscarOuFalhar(perfilId);
		perfilInputDisassembler.toDomainModel(input, perfil);
		return perfilRepository.save(perfil);
		
	}
	
	@Transactional
	public void associaPermissao(Long perfilId, Long permissaoId) {
		
		
		Perfil perfil = buscarOuFalhar(perfilId);
		Permissao permissao = permissaoService.buscarOuFalhar(permissaoId);
		
		perfil.associaPermissao(permissao);
		
	}
	
	@Transactional
	public void desassociaPermissao(Long perfilId, Long permissaoId) {
		
		Perfil perfil = buscarOuFalhar(perfilId);
		Permissao permissao = permissaoService.buscarOuFalhar(permissaoId);
		
		perfil.desassociaPermissao(permissao);
		
	}

}
