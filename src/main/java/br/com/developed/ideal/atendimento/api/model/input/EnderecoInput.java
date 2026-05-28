package br.com.developed.ideal.atendimento.api.model.input;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {

	@NotBlank
	private String logradouro;
	
	@NotBlank
	private String numero;
	
	private String complemento;
	
	@NotBlank
	private String bairro;
	
	@NotBlank
	private String cidade;
	
	@NotBlank
	private String uf;
	
	@NotBlank
	private String cep;
	
	private String referencia;
	
	@NotBlank
	private String ibge;
	
}
