package br.com.developed.ideal.atendimento.domain.model;

import java.time.OffsetDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Chamado {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Cliente cliente;
	
	private OffsetDateTime dataAbertura;
	private OffsetDateTime previsaoFechamento;
	private OffsetDateTime dataFechamento;
	
	private String assunto;
	
	@ManyToOne
	private Usuario usuarioTratamento;
	
	private Integer prioridade;
	
	private String descricao;
	
	private String descricaoFechamento;
	
	private String foneAbertura;
	
	@Enumerated(EnumType.STRING)
	private StatusChamado statusChamado;
	
	@OneToMany(mappedBy = "chamado", cascade = CascadeType.ALL)
	private List<ChamadoAnexo> anexos;

}
