package br.com.developed.ideal.atendimento.domain.model;

import java.time.OffsetDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ResetSenha {

	@EqualsAndHashCode.Include
	@Id
	private String id;
	
	private String codigoAutorizacao;
	
	private OffsetDateTime dataValidade;
	
	@ManyToOne
	private Usuario usuario;
	
	private Boolean executado;
	
}
