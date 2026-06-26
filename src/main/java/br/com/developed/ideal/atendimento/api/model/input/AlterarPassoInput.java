package br.com.developed.ideal.atendimento.api.model.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlterarPassoInput {
	
	@NotNull
	private Long sessaoId;
	
	@NotNull
	private Integer passo;

}
