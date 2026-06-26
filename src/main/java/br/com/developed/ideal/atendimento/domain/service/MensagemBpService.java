package br.com.developed.ideal.atendimento.domain.service;

import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.developed.ideal.atendimento.api.model.input.MensagemBpInput;
import br.com.developed.ideal.atendimento.domain.exception.MensagemNaoEncontradaException;
import br.com.developed.ideal.atendimento.domain.model.MensagemBp;
import br.com.developed.ideal.atendimento.domain.repository.MensagemBpRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MensagemBpService {
	
	private final MensagemBpRepository mensagemBpRepository;
	
	public String getMensagem(String nome) {
		
		String msg = "Olá ${nome}! Esse é um teste de mensagem";
		
		Map<String, Object> vars = Map.of(
				"nome", nome
				);
		
		return StringSubstitutor.replace(msg, vars);
		
	}
	
	public MensagemBp buscarOuFalharPorId(Long mensagemId) {
		return mensagemBpRepository.findById(mensagemId)
				.orElseThrow(() -> new MensagemNaoEncontradaException(mensagemId));
	}
	
	public MensagemBp buscarOuFalharPorChave(String chave) {
		return mensagemBpRepository.findByChave(chave)
				.orElseThrow(() -> new MensagemNaoEncontradaException(chave));
	}
	
	@Transactional
	public MensagemBp criar(MensagemBpInput input) {
		
		MensagemBp mensagemBp = new MensagemBp();
		
		mensagemBp.setChave(input.getChave());
		mensagemBp.setPayload(input.getPayload());
		mensagemBp.setTipo(input.getTipo());
		
		mensagemBp = mensagemBpRepository.save(mensagemBp);
		
		return mensagemBp;
		
	}
	
	@Transactional
	public MensagemBp alterar(Long mensagemId, MensagemBpInput input) {
		
		MensagemBp mensagemBp = buscarOuFalharPorId(mensagemId);
		
		mensagemBp.setChave(input.getChave());
		mensagemBp.setPayload(input.getPayload());
		mensagemBp.setTipo(input.getTipo());
		
		mensagemBp = mensagemBpRepository.save(mensagemBp);
		
		return mensagemBp;
		
	}
	
	@Transactional
	public void excluir(Long mensagemId) {
		
		mensagemBpRepository.deleteById(mensagemId);
		
	}

}
