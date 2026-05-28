package br.com.developed.ideal.atendimento.domain.exception;

public class GrupoUsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1543748476486605722L;
	
	public GrupoUsuarioNaoEncontradoException(Long grupoUsuarioId) {
		super(String.format("Não foi encontrada um grupo de usuário cadastrado com o ID %d.", grupoUsuarioId));
	}
	public GrupoUsuarioNaoEncontradoException(String identificadorBpms) {
		super(String.format("Não foi encontrada um grupo de usuário cadastrado com o identificador BPMS %s.", identificadorBpms));
	}
	

}
