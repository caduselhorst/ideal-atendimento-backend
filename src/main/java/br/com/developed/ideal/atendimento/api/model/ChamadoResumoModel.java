package br.com.developed.ideal.atendimento.api.model;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChamadoResumoModel {
	
	private Long id;
	
	private ClienteModel cliente;
	
	private OffsetDateTime dataAbertura;
	private OffsetDateTime previsaoFechamento;
	private OffsetDateTime dataFechamento;
	
	private String assunto;
	

	private UsuarioResumoModel usuarioTratamento;
	
	private Integer prioridade;
	
	private String descricao;
	
	private String descricaoFechamento;
	
	private String foneAbertura;
	
	private String statusChamado;
	

}
