package br.com.developed.ideal.atendimento.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.developed.ideal.atendimento.domain.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	Optional<Cliente> findByDocumentoAndInativo(String documento, boolean inativo);

}
