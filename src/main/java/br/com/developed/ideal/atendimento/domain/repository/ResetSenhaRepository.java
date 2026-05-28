package br.com.developed.ideal.atendimento.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.developed.ideal.atendimento.domain.model.ResetSenha;

public interface ResetSenhaRepository extends JpaRepository<ResetSenha, String> {

	Optional<ResetSenha> findByIdAndExecutado(String id, Boolean executado);
	
}
