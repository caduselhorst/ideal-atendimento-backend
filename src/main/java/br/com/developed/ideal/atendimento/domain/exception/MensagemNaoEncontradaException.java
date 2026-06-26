package br.com.developed.ideal.atendimento.domain.exception;

public class MensagemNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1543748476486605722L;
	
	public MensagemNaoEncontradaException(Long mensagemId) {
		super(String.format("Não foi encontrado um registro de mensagem cadastrada com o ID %d.", mensagemId));
	}

	public MensagemNaoEncontradaException(Integer passo) {
		super(String.format("Não foi encontrado um registro de mensagem cadastrada para o passo %d.", passo));
	}
	
	public MensagemNaoEncontradaException(String chave) {
		super(String.format("Não foi encontrado um registro de mensagem cadastrada com a chave %s.", chave));
	}
	

}
