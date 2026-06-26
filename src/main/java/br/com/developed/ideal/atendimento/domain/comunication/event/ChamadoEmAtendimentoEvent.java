package br.com.developed.ideal.atendimento.domain.comunication.event;

public record ChamadoEmAtendimentoEvent(
		Long chamadoId,
		String telefone,
		String nomeUsuario
) {}
