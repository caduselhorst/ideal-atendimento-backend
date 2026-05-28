package br.com.developed.ideal.atendimento.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.developed.ideal.atendimento.api.model.input.PerfilInput;
import br.com.developed.ideal.atendimento.domain.model.Perfil;

@Component
public class PerfilInputDisassembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public Perfil toDomainModel(PerfilInput input) {
		
		return mapper.map(input, Perfil.class);
		
	}
	
	public void toDomainModel(PerfilInput input, Perfil perfil) {
		
		mapper.map(input, perfil);
		
	}

}
