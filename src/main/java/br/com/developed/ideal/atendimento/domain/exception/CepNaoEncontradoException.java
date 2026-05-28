package br.com.developed.ideal.atendimento.domain.exception;

public class CepNaoEncontradoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8287479059825578903L;

	public CepNaoEncontradoException(String message, Throwable cause) {
		super(message, cause);
	}

	public CepNaoEncontradoException(String message) {
		super(message);
	}
	
	

}
