package br.com.developed.ideal.atendimento.api.model;

import java.util.List;

import br.com.developed.ideal.atendimento.domain.projection.AnalistaChamadoProjection;
import br.com.developed.ideal.atendimento.domain.projection.ClienteChamadoProjetion;
import br.com.developed.ideal.atendimento.domain.projection.DataChamadoProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashBoardModel {
	
	private Long quantidadeChamadosFila;
	private Long quantidadeChamadosComigo;
	private List<ClienteChamadoProjetion> clientesChamados;
	private List<AnalistaChamadoProjection> analistasChamados;
	private List<DataChamadoProjection> datasChamados;

}
