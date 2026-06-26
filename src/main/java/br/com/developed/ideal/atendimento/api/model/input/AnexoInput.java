package br.com.developed.ideal.atendimento.api.model.input;

import jakarta.validation.constraints.NotBlank;
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
public class AnexoInput {
	
	@NotBlank
	private String nomeArquivo;
	
	@NotBlank
	private String tipoArquivo;
	
	@NotBlank
	private String conteudoArquivo;

}
