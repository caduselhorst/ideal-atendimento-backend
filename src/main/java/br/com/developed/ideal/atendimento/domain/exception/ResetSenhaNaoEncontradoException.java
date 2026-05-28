package br.com.developed.ideal.atendimento.domain.exception;

public class ResetSenhaNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1543748476486605722L;
	
	public ResetSenhaNaoEncontradoException(String resetSenhaId) {
		super(String.format("Não foi encontrado um registro de reset de senha com o ID %s.", resetSenhaId));
	}

	
	

}
