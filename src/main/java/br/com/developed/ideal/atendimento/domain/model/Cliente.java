package br.com.developed.ideal.atendimento.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {
	
	@EqualsAndHashCode.Include
	@Id
	private Long id;
	private String razao;
	private String fantasia;
	private String documento;
	private Integer tipo;
	private Integer tipoCadastro;
	private String endereco;
	private String numero;
	private String bairro;
	private String cidade;
	private String uf;
	private String cep;
	private String codIbge;
	private boolean inativo;

}
