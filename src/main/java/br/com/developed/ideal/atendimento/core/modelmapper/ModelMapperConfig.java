package br.com.developed.ideal.atendimento.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.developed.ideal.atendimento.api.model.input.GrupoUsuarioInput;
import br.com.developed.ideal.atendimento.api.model.input.UsuarioInput;
import br.com.developed.ideal.atendimento.domain.model.GrupoUsuario;
import br.com.developed.ideal.atendimento.domain.model.Usuario;

@Configuration
public class ModelMapperConfig {
	
	
	@Bean
	ModelMapper getModelMapper() {
		
		ModelMapper modelMapper = new ModelMapper();
		
		
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		
		modelMapper.createTypeMap(UsuarioInput.class, Usuario.class)
			.addMappings(mapper -> mapper.skip(Usuario::setId));
		
		
		modelMapper.createTypeMap(GrupoUsuarioInput.class, GrupoUsuario.class)
			.addMappings(mapper -> mapper.skip(GrupoUsuario::setId));
		
				
		return modelMapper;
		
	}

}
