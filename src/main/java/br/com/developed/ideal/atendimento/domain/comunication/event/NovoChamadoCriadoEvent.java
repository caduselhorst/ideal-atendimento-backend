package br.com.developed.ideal.atendimento.domain.comunication.event;

public record NovoChamadoCriadoEvent(
	Long chamadoId,
	String razao,
	String fantasia,
	boolean web,
	String assunto,
	String contatoCliente
) {}


