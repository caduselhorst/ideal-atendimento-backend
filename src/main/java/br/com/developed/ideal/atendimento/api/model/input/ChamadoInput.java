package br.com.developed.ideal.atendimento.api.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChamadoInput {

	@NotNull
	private Long clienteId;
	
	@NotBlank
	private String assunto;
	
	@NotBlank
	private String descricao;
	
	private String foneAbertura;

}
