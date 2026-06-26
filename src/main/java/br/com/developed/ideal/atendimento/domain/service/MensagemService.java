package br.com.developed.ideal.atendimento.domain.service;

import java.time.OffsetDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.developed.ideal.atendimento.api.model.input.MensagemInput;
import br.com.developed.ideal.atendimento.domain.exception.MensagemNaoEncontradaException;
import br.com.developed.ideal.atendimento.domain.exception.NegocioException;
import br.com.developed.ideal.atendimento.domain.model.Mensagem;
import br.com.developed.ideal.atendimento.domain.repository.MensagemRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MensagemService {
	
	private final MensagemRepository mensagemRepository;
	
	private final UsuarioService usuarioService;
	
	public Mensagem buscarOuFalhar(Long mensagemId) {
		return mensagemRepository.findByIdAndDataExclusaoIsNull(mensagemId)
				.orElseThrow(() -> new MensagemNaoEncontradaException(mensagemId));
	}
	
	public Mensagem buscarOuFalharPorPasso(Integer passo) {
		return mensagemRepository.findByPassoAndDataExclusaoIsNull(passo)
				.orElseThrow(() -> new MensagemNaoEncontradaException(passo));
	}
	
	@Transactional
	public Mensagem criar(MensagemInput input) {
		
		try {
			
			Mensagem mensagem = new Mensagem();
			
			mensagem.setDataInclusao(OffsetDateTime.now());
			mensagem.setUsuarioInclusao(usuarioService.getUsuarioLogado());
			mensagem.setPasso(input.getPasso());
			mensagem.setMensagem(input.getMensagem());
			
			mensagem = mensagemRepository.saveAndFlush(mensagem);
			
			return mensagem;
			
		} catch (DataIntegrityViolationException e) {
			throw new NegocioException(String.format(
					"O passo informado [%d] já existe para mensagens. Verifique e tente novamente", 
					input.getPasso()));
		}
		
	}
	
	@Transactional
	public Mensagem alterar(Long mensagemId, MensagemInput input) {
		
		try {
			
			Mensagem mensagem = buscarOuFalhar(mensagemId);
			
			mensagem.setDataAlteracao(OffsetDateTime.now());
			mensagem.setUsuarioAlteracao(usuarioService.getUsuarioLogado());
			mensagem.setPasso(input.getPasso());
			mensagem.setMensagem(input.getMensagem());
			
			mensagem = mensagemRepository.saveAndFlush(mensagem);
			
			return mensagem;
			
		} catch (DataIntegrityViolationException e) {
			throw new NegocioException(String.format(
					"O passo informado [%d] já existe para mensagens. Verifique e tente novamente", 
					input.getPasso()));
		}
		
	}
	
	@Transactional
	public void excluir(Long mensagemId) {
		
		try {
			
			Mensagem mensagem = buscarOuFalhar(mensagemId);
			
			mensagem.setDataExclusao(OffsetDateTime.now());
			mensagem.setUsuarioExclusao(usuarioService.getUsuarioLogado());
			
			mensagem = mensagemRepository.saveAndFlush(mensagem);
			
		} catch (MensagemNaoEncontradaException e) {
			return;
		}
		
	}

}
