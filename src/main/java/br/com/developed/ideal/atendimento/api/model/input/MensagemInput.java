package br.com.developed.ideal.atendimento.api.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MensagemInput {
	
	@NotNull
	private Integer passo;
	
	@NotBlank
	private String mensagem;

}
