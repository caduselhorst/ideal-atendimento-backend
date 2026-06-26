package br.com.developed.ideal.atendimento.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class ValidarCnpjModel {
	
	private String cnpj;
	
	@JsonProperty("isValido")
	private boolean valido;

}
