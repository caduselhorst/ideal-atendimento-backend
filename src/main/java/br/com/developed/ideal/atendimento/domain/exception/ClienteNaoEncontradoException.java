package br.com.developed.ideal.atendimento.domain.exception;

public class ClienteNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1543748476486605722L;
	
	public ClienteNaoEncontradoException(Long clienteId) {
		super(String.format("Não foi encontrado um cliente cadastrado com o ID %d.", clienteId));
	}

	public ClienteNaoEncontradoException(String documento) {
		super(String.format("Não foi encontrado um cliente cadastrado com o documento %s", documento));
	}

}
