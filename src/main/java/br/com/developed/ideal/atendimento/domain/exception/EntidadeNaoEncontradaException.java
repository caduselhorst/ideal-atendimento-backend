package br.com.developed.ideal.atendimento.domain.exception;

public abstract class EntidadeNaoEncontradaException extends NegocioException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7872257515621832671L;


	public EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public EntidadeNaoEncontradaException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}


}
