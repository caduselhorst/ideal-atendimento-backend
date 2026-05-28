package br.com.developed.ideal.atendimento.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Permissao {
	
	@EqualsAndHashCode.Include
	@Id
	private Long id;
	private String nome;
	private String descricao;

}
