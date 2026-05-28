package br.com.developed.ideal.atendimento.domain.exception;

public class PerfilNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1543748476486605722L;
	
	public PerfilNaoEncontradoException(Long perfilId) {
		super(String.format("Não foi encontrado um perfil cadastrado com o ID %d.", perfilId));
	}

	
	

}
