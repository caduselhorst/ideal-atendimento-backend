package br.com.developed.ideal.atendimento.api.model.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInput {

	@NotBlank
	private String login;
	
	@NotBlank
	private String nome;
	private String fone;
	
	@Email
	private String email;
	
	@NotBlank
	private String documento;
	
	@NotNull
	private Boolean habilitaNotificacaoWhatsapp;

	
}
