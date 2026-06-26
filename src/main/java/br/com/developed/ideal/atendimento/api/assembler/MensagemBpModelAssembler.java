package br.com.developed.ideal.atendimento.api.assembler;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import br.com.developed.ideal.atendimento.api.model.MensagemBpModel;
import br.com.developed.ideal.atendimento.domain.model.MensagemBp;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MensagemBpModelAssembler {
	
	private final ModelMapper mapper;
	
	public MensagemBpModel toModel(MensagemBp mensagemBp) {
		return mapper.map(mensagemBp, MensagemBpModel.class);
	}
	
	public List<MensagemBpModel> toListModel(List<MensagemBp> mensagens) {
		return mensagens
				.stream()
				.map(this::toModel)
				.toList();
	}
	
	public Page<MensagemBpModel> toPageModel(Page<MensagemBp> page) {
		
		List<MensagemBpModel> listModel = toListModel(page.getContent());
		
		Page<MensagemBpModel> pageModel = new PageImpl<>(
				listModel, page.getPageable(), page.getTotalElements());
		
		return pageModel;
		
	}

}
