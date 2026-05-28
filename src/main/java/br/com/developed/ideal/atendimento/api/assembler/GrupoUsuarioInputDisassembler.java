package br.com.developed.ideal.atendimento.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.developed.ideal.atendimento.api.model.input.GrupoUsuarioInput;
import br.com.developed.ideal.atendimento.domain.model.GrupoUsuario;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class GrupoUsuarioInputDisassembler {

	private ModelMapper mapper;
	
	public GrupoUsuario toDomainModel(GrupoUsuarioInput input) {
		
		return mapper.map(input, GrupoUsuario.class);
				
	}
	
	public void toDomainModel(GrupoUsuarioInput input, GrupoUsuario grupo) {
		mapper.map(input, grupo);
	}
	
}
