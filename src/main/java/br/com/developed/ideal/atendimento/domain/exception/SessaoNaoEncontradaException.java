package br.com.developed.ideal.atendimento.domain.exception;

public class SessaoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1543748476486605722L;
	
	public SessaoNaoEncontradaException(Long sessaoId) {
		super(String.format("Não foi encontrado uma sessão cadastrada com o ID %d.", sessaoId));
	}

	
	

}
