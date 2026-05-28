package br.com.developed.ideal.atendimento.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerfilResumoModel {
	
	private Long id;
	private String nome;
	private String descricao;
	private Boolean recebeAgendamento;
	private Boolean padrao;

}
