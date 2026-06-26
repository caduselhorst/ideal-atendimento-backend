package br.com.developed.ideal.atendimento.api.assembler;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import br.com.developed.ideal.atendimento.api.model.ChamadoModel;
import br.com.developed.ideal.atendimento.api.model.ChamadoResumoModel;
import br.com.developed.ideal.atendimento.domain.model.Chamado;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChamadoModelAssembler {
	
	private final ModelMapper mapper;
	
	public ChamadoModel toModel(Chamado chamado) {
		return mapper.map(chamado, ChamadoModel.class);
	}
	
	public ChamadoResumoModel toResumoModel(Chamado chamado) {
		return mapper.map(chamado, ChamadoResumoModel.class);
	}
	
	public List<ChamadoResumoModel> toListModel(List<Chamado> chamados) {
		return chamados
				.stream()
				.map(this::toResumoModel)
				.toList();
	}
	
	public Page<ChamadoResumoModel> toPageModel(Page<Chamado> page) {
		
		List<ChamadoResumoModel> listModel = toListModel(page.getContent());
		
		Page<ChamadoResumoModel> pageModel = new PageImpl<>(
				listModel, page.getPageable(), page.getTotalElements());
		
		return pageModel;
		
	}

}
