package br.com.developed.ideal.atendimento.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.developed.ideal.atendimento.api.model.ClienteModel;
import br.com.developed.ideal.atendimento.domain.model.Cliente;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClienteModelAssembler {
	
	private final ModelMapper mapper;
	
	public ClienteModel toModel (Cliente cliente) {
		return mapper.map(cliente, ClienteModel.class);
	}

}
