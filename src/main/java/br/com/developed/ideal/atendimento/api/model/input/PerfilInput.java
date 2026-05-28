package br.com.developed.ideal.atendimento.api.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerfilInput {

	@NotBlank
	private String nome;
	private String descricao;
	
	@NotNull
	private Boolean recebeAgendamento;
	
	@NotNull
	private Boolean padrao;

}
