package br.com.developed.ideal.atendimento.api.model;

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
public class SessaoModel {
	
	private Long id;
	private Integer passo;
	private String numero;
	private String ultimoTextoDigitado;
	private String tipoUltimoTextoDigitado;
	private String identificador;
	private Long chamadoId;

}
