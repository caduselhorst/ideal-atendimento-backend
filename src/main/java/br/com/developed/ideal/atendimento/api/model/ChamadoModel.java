package br.com.developed.ideal.atendimento.api.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChamadoModel extends ChamadoResumoModel {
	
	private List<ChamadoAnexoModel> anexos;

}
