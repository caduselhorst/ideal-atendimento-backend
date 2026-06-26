package br.com.developed.ideal.atendimento.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.developed.ideal.atendimento.domain.model.MensagemBp;

public interface MensagemBpRepository extends JpaRepository<MensagemBp, Long> {
	
	Optional<MensagemBp> findByChave(String chave);

}
