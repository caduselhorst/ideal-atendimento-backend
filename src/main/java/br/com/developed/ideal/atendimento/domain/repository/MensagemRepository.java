package br.com.developed.ideal.atendimento.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.developed.ideal.atendimento.domain.model.Mensagem;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
	
	@EntityGraph(attributePaths = {
		"usuarioInclusao",
		"usuarioAlteracao",
		"usuarioExclusao",
	})
	Optional<Mensagem> findByIdAndDataExclusaoIsNull(Long id);
	
	@EntityGraph(attributePaths = {
			"usuarioInclusao",
			"usuarioAlteracao",
			"usuarioExclusao",
		})
	Optional<Mensagem> findByPassoAndDataExclusaoIsNull(Integer passo);
	
	@EntityGraph(attributePaths = {
			"usuarioInclusao",
			"usuarioAlteracao",
			"usuarioExclusao",
		})
	Page<Mensagem> findByDataExclusaoIsNull(Pageable pageable);
	
	List<Mensagem> findByDataExclusaoIsNull();

}
