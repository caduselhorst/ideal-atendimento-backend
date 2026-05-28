package br.com.developed.ideal.atendimento.domain.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Endereco {
	
	private String logradouro;
	private String numero;
	private  String complemento;
	private String bairro;
	private String cidade;
	private String uf;
	private String cep;
	private String referencia;
	private String ibge;

}
