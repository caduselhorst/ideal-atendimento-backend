package br.com.developed.ideal.atendimento.domain.exception;

public class UsuarioEmailNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1543748476486605722L;
	
	public UsuarioEmailNaoEncontradoException(String email) {
		super(String.format("Não foi encontrado o email %s registrado no mail server.", email));
	}

	
	

}
