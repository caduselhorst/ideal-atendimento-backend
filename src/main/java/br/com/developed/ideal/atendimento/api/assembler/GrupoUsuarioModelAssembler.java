package br.com.developed.ideal.atendimento.api.assembler;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import br.com.developed.ideal.atendimento.api.model.GrupoUsuarioModel;
import br.com.developed.ideal.atendimento.domain.model.GrupoUsuario;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class GrupoUsuarioModelAssembler {

	private ModelMapper mapper;
	
	public GrupoUsuarioModel toModel(GrupoUsuario grupo) {
		
		return mapper.map(grupo, GrupoUsuarioModel.class);
		
	}
	
	public List<GrupoUsuarioModel> toListModel(List<GrupoUsuario> grupos) {
		
		return grupos
				.stream()
				.map(this::toModel)
				.toList();
		
	}
	
	public Page<GrupoUsuarioModel> toPageModel(Page<GrupoUsuario> page) {
		
		List<GrupoUsuarioModel> listModel = toListModel(page.getContent());
		
		Page<GrupoUsuarioModel> pageModel = new PageImpl<>(listModel, 
				page.getPageable(), page.getTotalElements());
		
		return pageModel;
		
	}
	
}
