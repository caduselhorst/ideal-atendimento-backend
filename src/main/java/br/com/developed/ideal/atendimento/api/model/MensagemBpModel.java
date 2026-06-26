package br.com.developed.ideal.atendimento.api.model;

import br.com.developed.ideal.atendimento.domain.model.TipoMensagemBP;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MensagemBpModel {
	
	private Long id;
	private String chave;
	private TipoMensagemBP tipo;
	
	private String payload;

}
