package br.com.developed.ideal.atendimento.api.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NovoChamadoInput {
	
	@NotNull
	private Long clienteId;
	
	@NotBlank
	private String assunto;
	
	@NotBlank
	private String descricao;
	
	@NotNull
	private Integer prioridade;
	
	@NotBlank
	private String contato;

}
