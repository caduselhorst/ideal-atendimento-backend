package br.com.developed.ideal.atendimento.api.model.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalvarChamadoInput {
	
	private Integer prioridade;
	private String descricaoFechamento;

}
