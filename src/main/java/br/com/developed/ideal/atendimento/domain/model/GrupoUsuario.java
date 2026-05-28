package br.com.developed.ideal.atendimento.domain.model;

import java.time.OffsetDateTime;
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
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class GrupoUsuario {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String descricao;
	
	private String identificadorbpms;
	
	private OffsetDateTime dataInclusao;
	private OffsetDateTime dataAlteracao;
	private OffsetDateTime dataExclusao;
	
	@ManyToOne
	private Usuario usuarioResponsavel;
	
	@ManyToOne
	public Usuario usuarioInclusao;
	
	@ManyToOne
	public Usuario usuarioAlteracao;
	
	@ManyToOne
	public Usuario usuarioExclusao;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_grupo", 
		joinColumns = @JoinColumn(name = "grupoUsuarioId"), 
		inverseJoinColumns = @JoinColumn(name = "usuarioId"))
	private List<Usuario> usuarios = new ArrayList<>();
	
	public void adicionaUsuario(Usuario usuario) {
		if (!usuarios.contains(usuario)) {
			usuarios.add(usuario);
		}
	}
	
	public void removeUsuario(Usuario usuario) {
		if (usuarios.contains(usuario)) {			
			usuarios.remove(usuarios.indexOf(usuario));
		}
	}
	
}
