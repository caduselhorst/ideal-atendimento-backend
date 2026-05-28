package br.com.developed.ideal.atendimento.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.developed.ideal.atendimento.api.model.input.UsuarioInput;
import br.com.developed.ideal.atendimento.domain.model.Usuario;

@Component
public class UsuarioInputDisassembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public Usuario toDomainModel(UsuarioInput input) {
		
		return mapper.map(input, Usuario.class);
		
	}
	
	public void toDomainModel(UsuarioInput input, Usuario usuario) {
		
		mapper.map(input, usuario);
		
	}

}
