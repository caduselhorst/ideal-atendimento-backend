package br.com.developed.ideal.atendimento.domain.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Perfil {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String descricao;
	private Boolean padrao;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "perfil_permissao",
			joinColumns = @JoinColumn(name = "perfil_id"),
			inverseJoinColumns = @JoinColumn(name = "permissao_id"))
	private List<Permissao> permissoes = new ArrayList<>();
	
	public void associaPermissao(Permissao permissao) {
		permissoes.add(permissao);
	}
	
	public void desassociaPermissao(Permissao permissao) {
		permissoes.remove(permissao);
	}

}
