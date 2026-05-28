package br.com.developed.ideal.atendimento.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.developed.ideal.atendimento.domain.model.GrupoUsuario;

public interface GrupoUsuarioRepository extends JpaRepository<GrupoUsuario, Long> {
	
	List<GrupoUsuario> findByDataExclusaoIsNullAndDescricaoContainsIgnoreCase(String descricao);
	Page<GrupoUsuario> findByDataExclusaoIsNullAndDescricaoContainsIgnoreCase(String descricao, Pageable pageable);
	Optional<GrupoUsuario> findByIdentificadorbpms(String identificadorbpms);

}
