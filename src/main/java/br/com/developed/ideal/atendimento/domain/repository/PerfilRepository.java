package br.com.developed.ideal.atendimento.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.developed.ideal.atendimento.domain.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
	
	@Modifying
	@Query("update Perfil set padrao=false")
	public void atualizaPadrao();

}
