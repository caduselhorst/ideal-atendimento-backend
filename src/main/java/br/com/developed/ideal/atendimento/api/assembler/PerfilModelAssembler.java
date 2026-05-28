package br.com.developed.ideal.atendimento.api.assembler;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.developed.ideal.atendimento.api.model.PerfilModel;
import br.com.developed.ideal.atendimento.api.model.PerfilResumoModel;
import br.com.developed.ideal.atendimento.domain.model.Perfil;

@Component
public class PerfilModelAssembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public List<PerfilResumoModel> toListResumoModel(List<Perfil> perfis) {
		
		return perfis
				.stream()
				.map(p -> mapper.map(p, PerfilResumoModel.class))
				.toList();
		
	}
	
	public PerfilModel toModel(Perfil perfil) {
		
		return mapper.map(perfil, PerfilModel.class);
				
	}

}
