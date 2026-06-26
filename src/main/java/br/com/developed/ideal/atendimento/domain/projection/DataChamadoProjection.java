package br.com.developed.ideal.atendimento.domain.projection;

import java.time.LocalDate;

public interface DataChamadoProjection {

	LocalDate getDia();
	Long getQuantidade();
	
}
