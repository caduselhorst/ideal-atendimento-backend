package br.com.developed.ideal.atendimento.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = -4450751896944427802L;

	public UsuarioNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public UsuarioNaoEncontradoException(Long usuarioId) {
		super(String.format("Não foi encontrado um usuaŕio cadastrado com o id %d", usuarioId));
	}

}
