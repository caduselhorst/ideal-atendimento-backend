package br.com.developed.ideal.atendimento.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoUsuarioModel {
	
	private Long id;
	
	private String descricao;
	
	private String identificadorbpms;
	
	private UsuarioModel usuarioResponsavel;
	

}
