package br.com.developed.ideal.atendimento.domain.service;

import org.springframework.stereotype.Service;

import br.com.developed.ideal.atendimento.domain.exception.PermissaoNaoEncontradaException;
import br.com.developed.ideal.atendimento.domain.model.Permissao;
import br.com.developed.ideal.atendimento.domain.repository.PermissaoRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PermissaoService {
	
	private PermissaoRepository permissaoRepository;
	
	public Permissao buscarOuFalhar(Long permissaoId) {
		
		return permissaoRepository.findById(permissaoId)
				.orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
		
	}

}
