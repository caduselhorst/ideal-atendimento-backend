package br.com.developed.ideal.atendimento.domain.exception;

public class StorageException extends RuntimeException {

	private static final long serialVersionUID = 7557261590351325440L;
	
	public StorageException(String message) {
		super(message);
	}
	
	public StorageException(Throwable t) {
		super(t);
	}
	
	public StorageException(String message, Throwable t) {
		super(message, t);
	}

}
