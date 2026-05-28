package br.com.developed.ideal.atendimento.domain.exception;

public class NegocioException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -787260577764378675L;

	public NegocioException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	public NegocioException(String mensagem, Throwable causa) {
		super(mensagem, causa);
		
	}
	

}
