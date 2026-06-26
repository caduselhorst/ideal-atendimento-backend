package br.com.developed.ideal.atendimento.domain.exception;

public class ErroExternoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2527671040561978890L;

	public ErroExternoException(String message, Throwable cause) {
		super(message, cause);
	}

	public ErroExternoException(String message) {
		super(message);
	}
	
	

}
