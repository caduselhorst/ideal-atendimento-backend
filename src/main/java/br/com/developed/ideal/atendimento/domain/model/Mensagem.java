package br.com.developed.ideal.atendimento.domain.model;

import java.time.OffsetDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Mensagem {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer passo;
	private String mensagem;
	
	private OffsetDateTime dataInclusao;
	private OffsetDateTime dataAlteracao;
	private OffsetDateTime dataExclusao;
	
	@ManyToOne
	private Usuario usuarioInclusao;
	
	@ManyToOne
	private Usuario usuarioAlteracao;
	
	@ManyToOne
	private Usuario usuarioExclusao;

}
