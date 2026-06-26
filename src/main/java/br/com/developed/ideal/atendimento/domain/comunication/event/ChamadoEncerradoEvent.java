package br.com.developed.ideal.atendimento.domain.comunication.event;

public record ChamadoEncerradoEvent(
	Long chamadoId,
	String descricaoFechamento,
	String usuario,
	String telefone,
	boolean cancelado
) {}
