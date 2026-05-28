package br.com.developed.ideal.atendimento.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1543748476486605722L;
	
	public PermissaoNaoEncontradaException(Long permissaoId) {
		super(String.format("Não foi encontrado uma permissão cadastrada com o ID %d.", permissaoId));
	}

	
	

}
