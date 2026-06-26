package br.com.developed.ideal.atendimento.domain.repository;


import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.developed.ideal.atendimento.domain.model.Chamado;
import br.com.developed.ideal.atendimento.domain.model.StatusChamado;
import br.com.developed.ideal.atendimento.domain.model.Usuario;
import br.com.developed.ideal.atendimento.domain.projection.AnalistaChamadoProjection;
import br.com.developed.ideal.atendimento.domain.projection.ClienteChamadoProjetion;
import br.com.developed.ideal.atendimento.domain.projection.DataChamadoProjection;

public interface ChamadoRepository 
		extends JpaRepository<Chamado, Long>, JpaSpecificationExecutor<Chamado> {
	
	
	@EntityGraph(attributePaths = {"cliente", "usuarioTratamento", "anexos"})
	Optional<Chamado> findById(Long chamadoId);
	
	@EntityGraph(attributePaths = {"cliente", "usuarioTratamento"})
	Page<Chamado> findByStatusChamadoAndUsuarioTratamento(StatusChamado status, Usuario usuarioTratamento, Pageable pageable);
	
	Long countByStatusChamadoAndUsuarioTratamento(StatusChamado status, Usuario usuario);
	
	Long countByDataFechamentoGreaterThanEqualAndDataFechamentoLessThanEqual(
			OffsetDateTime dataInicio, OffsetDateTime dataFim);
	
	Long countByDataAberturaGreaterThanEqualAndDataFechamentoLessThanEqual(
			OffsetDateTime dataInicio, OffsetDateTime dataFim);
	
	@Query("""
			select new br.com.developed.ideal.atendimento.domain.projection.ClienteChamadoProjetion(
			     cli.razao,
			     cli.fantasia,
			     count(c)
			 )
			 from Chamado c
			 join c.cliente cli
			 where c.dataAbertura >= :dataAbertura
			 group by cli.razao, cli.fantasia
			 order by count(c) desc
			""")
	List<ClienteChamadoProjetion> contaChamadosCliente(@Param("dataAbertura") OffsetDateTime dataAbertura, Pageable pageable);
	
	@Query("""
			select new br.com.developed.ideal.atendimento.domain.projection.AnalistaChamadoProjection(
			     u.nome as analista,
			     count(c)
			 )
			 from Chamado c
			 join c.usuarioTratamento u
			 where c.dataAbertura >= :dataAbertura
			 	and c.statusChamado = 'FINALIZADO'
			 group by u.nome
			 order by count(c) desc
			""")
	List<AnalistaChamadoProjection> contaChamadosAnalista(@Param("dataAbertura") OffsetDateTime dataAbertura, Pageable pageable);
	
	
	@Query(nativeQuery = true, value = 
			"""
			SELECT
				DATE(CONVERT_TZ(data_abertura,'+00:00','-03:00')) AS dia,
				COUNT(*) AS quantidade
			FROM chamado
			WHERE data_abertura >= :dataAbertura
			GROUP BY dia
			ORDER BY dia
			""")
	List<DataChamadoProjection> contaChamadosData(@Param("dataAbertura") OffsetDateTime dataAbertura);
	
	@Override
	@EntityGraph(attributePaths = {
			"cliente",
			"usuarioTratamento"
	})
	Page<Chamado> findAll(
			Specification<Chamado> spec,
			Pageable pageable);

}
