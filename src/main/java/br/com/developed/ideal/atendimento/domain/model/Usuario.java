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
public class Usuario {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String login;
	private String nome;
	private String senha;
	private String fone;
	private String email;
	private String documento;
	
	private Boolean habilitaNotificacaoWhatsapp;
	
	private boolean inativo;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_perfil",
		joinColumns = @JoinColumn(name = "usuario_id"),
		inverseJoinColumns = @JoinColumn(name = "perfil_id"))
	private List<Perfil> perfis = new ArrayList<>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_grupo",
		joinColumns = @JoinColumn(name = "usuarioId"),
		inverseJoinColumns = @JoinColumn(name = "grupoUsuarioId"))
	private List<GrupoUsuario> grupos = new ArrayList<>();
	
	
	public void associaPerfil(Perfil perfil) {
		perfis.add(perfil);
	}
		
	public void desassociaPerfil(Perfil perfil) {
		perfis.remove(perfil);
	}
	
	
	public void inativar() {
		setInativo(true);
	}
	
	public void ativar() {
		setInativo(false);
	}
	
	public void alteraSenha(String novaSenha) {
		this.senha = novaSenha;
	}
	
	public void adicionaGrupo(GrupoUsuario grupo) {
		grupos.add(grupo);
	}
	
	public void removeGrupo(GrupoUsuario grupo) {
		grupos.remove(grupo);
	}

}
