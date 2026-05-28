package br.com.developed.ideal.atendimento.api.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel {

	private Long id;
	private String login;
	private String nome;
	private String fone;
	private String documento;
	private String email;
	private boolean inativo;
	private boolean habilitaNotificacaoWhatsapp;
	private List<GrupoUsuarioResumoModel> grupos; 
	
}
