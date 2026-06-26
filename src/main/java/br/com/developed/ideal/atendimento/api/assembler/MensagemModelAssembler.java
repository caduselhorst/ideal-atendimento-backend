package br.com.developed.ideal.atendimento.api.assembler;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import br.com.developed.ideal.atendimento.api.model.MensagemModel;
import br.com.developed.ideal.atendimento.api.model.MensagemResumoModel;
import br.com.developed.ideal.atendimento.domain.model.Mensagem;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MensagemModelAssembler {
	
	private final ModelMapper mapper;
	
	public MensagemResumoModel toResumoModel(Mensagem mensagem) {
		return mapper.map(mensagem, MensagemResumoModel.class);
	}
	
	public MensagemModel toModel(Mensagem mensagem) {
		return mapper.map(mensagem, MensagemModel.class);
	}

	public List<MensagemResumoModel> toListModel(List<Mensagem> mensagens) {
		return mensagens
				.stream()
				.map(this::toResumoModel)
				.toList();
	}
	
	public Page<MensagemResumoModel> toPageModel(Page<Mensagem> pageMensagem) {
		
		List<MensagemResumoModel> listModel = toListModel(pageMensagem.getContent());
		
		Page<MensagemResumoModel> pageModel = new PageImpl<>(
				listModel, pageMensagem.getPageable(), pageMensagem.getTotalElements());
		
		return pageModel;
		
	}
	
}
