package br.com.developed.ideal.atendimento.domain.comunication.event;

import br.com.developed.ideal.atendimento.domain.model.Chamado;
import br.com.developed.ideal.atendimento.domain.model.Usuario;

public record AtribuirChamadoEvent(
	Chamado chamado,
	Usuario usuarioAtribuidor,
	Usuario usuarioAtribuido
) {}
