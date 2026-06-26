package br.com.developed.ideal.atendimento.domain.exception;

public class ChamadoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1543748476486605722L;
	
	public ChamadoNaoEncontradoException(Long chamadoId) {
		super(String.format("Não foi encontrado um chamado cadastrado com o ID %d.", chamadoId));
	}

	public ChamadoNaoEncontradoException(Long chamadoId, String documento) {
		super(String.format("Não foi encontrado um chamado cadastrado com o ID %d para o documento %s.", chamadoId, documento));
	}
	

}
