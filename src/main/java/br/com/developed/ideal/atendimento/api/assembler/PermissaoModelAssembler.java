package br.com.developed.ideal.atendimento.api.assembler;

import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.developed.ideal.atendimento.api.model.PermissaoModel;
import br.com.developed.ideal.atendimento.domain.model.Permissao;

@Component
public class PermissaoModelAssembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public List<PermissaoModel> toListModel(Collection<Permissao> permissoes) {
		
		return permissoes
				.stream()
				.map(p -> mapper.map(p, PermissaoModel.class))
				.toList();
		
	}
	


}
