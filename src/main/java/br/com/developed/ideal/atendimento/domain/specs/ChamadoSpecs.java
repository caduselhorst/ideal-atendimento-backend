package br.com.developed.ideal.atendimento.domain.specs;

import java.util.ArrayList;

import org.springframework.data.jpa.domain.Specification;

import br.com.developed.ideal.atendimento.domain.filter.ChamadoFilter;
import br.com.developed.ideal.atendimento.domain.model.Chamado;
import br.com.developed.ideal.atendimento.domain.model.Cliente;
import br.com.developed.ideal.atendimento.domain.model.Usuario;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public class ChamadoSpecs {
	
	public static Specification<Chamado> usandoFiltro (ChamadoFilter filter) {
		
		return (root, query, builder) -> {
			
			var predicates = new ArrayList<Predicate>();

			Join<Chamado, Cliente> clienteJoin = null;
			Join<Chamado, Usuario> usuarioJoin = null;

			// Filtros do chamado

			if (filter.getId() != null) {
				predicates.add(
						builder.equal(root.get("id"), filter.getId()));
			}

			if (filter.getDataAberturaInicial() != null) {
				predicates.add(
						builder.greaterThanOrEqualTo(
								root.get("dataAbertura"),
								filter.getDataAberturaInicial()));
			}

			if (filter.getDataAberturaFinal() != null) {
				predicates.add(
						builder.lessThanOrEqualTo(
								root.get("dataAbertura"),
								filter.getDataAberturaFinal()));
			}

			if (filter.getDataFechamentoInicial() != null) {
				predicates.add(
						builder.greaterThanOrEqualTo(
								root.get("dataFechamento"),
								filter.getDataFechamentoInicial()));
			}

			if (filter.getDataFechamentoFinal() != null) {
				predicates.add(
						builder.lessThanOrEqualTo(
								root.get("dataFechamento"),
								filter.getDataFechamentoFinal()));
			}

			if (filter.getStatusChamado() != null) {
				predicates.add(
						builder.equal(
								root.get("statusChamado"),
								filter.getStatusChamado()));
			}

			if (possuiTexto(filter.getAssunto())) {
				predicates.add(
						builder.like(
								builder.lower(root.get("assunto")),
								"%" + filter.getAssunto().trim().toLowerCase() + "%"));
			}

			if (filter.getPrioridade() != null) {
				predicates.add(
						builder.equal(
								root.get("prioridade"),
								filter.getPrioridade()));
			}

			// Join com usuário apenas se necessário

			if (filter.getUsuarioTratamentoId() != null) {

				usuarioJoin = root.join("usuarioTratamento", JoinType.LEFT);

				predicates.add(
						builder.equal(
								usuarioJoin.get("id"),
								filter.getUsuarioTratamentoId()));
			}

			// Join com cliente apenas se necessário

			if (filter.getClienteId() != null
					|| possuiTexto(filter.getRazao())
					|| possuiTexto(filter.getFantasia())) {

				clienteJoin = root.join("cliente", JoinType.LEFT);
			}

			if (filter.getClienteId() != null) {
				predicates.add(
						builder.equal(
								clienteJoin.get("id"),
								filter.getClienteId()));
			}

			if (possuiTexto(filter.getRazao())) {
				predicates.add(
						builder.like(
								builder.lower(clienteJoin.get("razao")),
								"%" + filter.getRazao().trim().toLowerCase() + "%"));
			}

			if (possuiTexto(filter.getFantasia())) {
				predicates.add(
						builder.like(
								builder.lower(clienteJoin.get("fantasia")),
								"%" + filter.getFantasia().trim().toLowerCase() + "%"));
			}

			return builder.and(predicates.toArray(new Predicate[0]));
			
		};
		
	}
	
	private static boolean possuiTexto(String valor) {
		return valor != null && !valor.isBlank();
	}

}
