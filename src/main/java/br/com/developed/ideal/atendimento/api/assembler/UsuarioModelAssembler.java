package br.com.developed.ideal.atendimento.api.assembler;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import br.com.developed.ideal.atendimento.api.model.UsuarioModel;
import br.com.developed.ideal.atendimento.domain.model.Usuario;

@Component
public class UsuarioModelAssembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public UsuarioModel toModel(Usuario usuario) {
		return mapper.map(usuario, UsuarioModel.class);
	}
	
	public List<UsuarioModel> toListModel(List<Usuario> usuarios) {
		return usuarios.stream().map(this::toModel).toList();
	}
	
	public Page<UsuarioModel> toPageModel(Page<Usuario> usuarios) {
		
		List<UsuarioModel> usuariosModel = toListModel(usuarios.getContent());
		
		Page<UsuarioModel> usuariosPage = new PageImpl<>(
				usuariosModel, usuarios.getPageable(), usuarios.getTotalElements());
		
		return usuariosPage;
	}

}
