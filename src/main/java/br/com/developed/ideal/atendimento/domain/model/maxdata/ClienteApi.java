package br.com.developed.ideal.atendimento.domain.model.maxdata;

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
public class ClienteApi {

	private Long cliid;
	private String clinome;
	private String clicpfcgc;
	private String clifantasia;
	private Integer clitipocad;
	private Integer clitipo;
	private String clifatend;
	private String clifatendnumero;
	private String clifatbairro;
	private String clifatcidade;
	private String clifatuf;
	private String clifatcep;
	private String clifatcidcodibge;
	private Integer clidesativa;
	private Integer cliusuloginid;
	
}
