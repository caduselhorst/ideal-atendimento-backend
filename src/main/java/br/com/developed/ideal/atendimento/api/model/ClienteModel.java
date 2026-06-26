package br.com.developed.ideal.atendimento.api.model;

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
public class ClienteModel {
	
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
