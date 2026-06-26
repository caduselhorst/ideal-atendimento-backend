package br.com.developed.ideal.atendimento.domain.filter;

import java.time.OffsetDateTime;

import br.com.developed.ideal.atendimento.domain.model.StatusChamado;
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
public class ChamadoFilter {
	
	private Long id;
	
	private OffsetDateTime dataAberturaInicial;
	private OffsetDateTime dataAberturaFinal;
	
	private OffsetDateTime dataFechamentoInicial;
	private OffsetDateTime dataFechamentoFinal;
	
	private StatusChamado statusChamado;
	
	private Long usuarioTratamentoId;
	
	private String assunto;
	
	private Integer prioridade;
	
	private Long clienteId;
	
	private String razao;
	
	private String fantasia;
	
	

}
