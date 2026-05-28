package br.com.developed.ideal.atendimento.api.model.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlterarSenhaInput {

	@NotBlank
	private String senhaAtual;
	
	@NotBlank
	private String novaSenha;
	
	@NotBlank
	private String confirmacaoNovaSenha;
	
}
