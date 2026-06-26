package br.com.developed.ideal.atendimento.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MensagemResumoModel {
	
	private Long id;
	private Integer passo;
	private String mensagem;

}
