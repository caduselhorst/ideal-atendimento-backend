package br.com.developed.ideal.atendimento.domain.exception;

public class EntidadeEmUsoException extends NegocioException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -754025580331360223L;

	public EntidadeEmUsoException(String message) {
		super(message);
	}
	
	public EntidadeEmUsoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	
}
