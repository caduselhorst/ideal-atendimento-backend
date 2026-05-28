package br.com.developed.ideal.atendimento.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.developed.ideal.atendimento.domain.model.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

}
