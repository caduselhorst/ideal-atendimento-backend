package br.com.developed.ideal.atendimento.api.model.input;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
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
@Valid
public class AnexosInput {

	@NotNull
	private List<AnexoInput> anexos;
	
	public void adicionarAnexo(AnexoInput anexo) {
		
		if (anexos == null) {
			anexos = new ArrayList<>();
		}
		
		anexos.add(anexo);
		
	}
	
	
}
