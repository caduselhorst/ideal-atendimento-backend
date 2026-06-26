package br.com.developed.ideal.atendimento.api.model.input;

import br.com.developed.ideal.atendimento.core.validation.Json;
import br.com.developed.ideal.atendimento.domain.model.TipoMensagemBP;
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
public class MensagemBpInput {
	
	@NotBlank
	private String chave;
	
	@NotNull
	private TipoMensagemBP tipo;
	
	@NotBlank
	@Json
	private String payload;

}
