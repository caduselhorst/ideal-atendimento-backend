package br.com.developed.ideal.atendimento.domain.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.developed.ideal.atendimento.domain.exception.ClienteNaoEncontradoException;
import br.com.developed.ideal.atendimento.domain.model.Cliente;
import br.com.developed.ideal.atendimento.domain.model.maxdata.ClienteApi;
import br.com.developed.ideal.atendimento.domain.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClienteService {
	
	private final ClienteRepository clienteRepository;
	
	private final MaxdataService maxdataService;
	
	
	public Cliente buscarPorDocumento(@NonNull String documento) {
		return clienteRepository.findByDocumentoAndInativo(documento, false)
				.orElse(null);
	}
	
	public Cliente buscarPorIdOuFalhar(@NonNull Long clienteId) {
		return clienteRepository.findById(clienteId)
				.orElseThrow(() -> new ClienteNaoEncontradoException(clienteId));
	}
	
	@Transactional
	public Cliente buscarPorDocumentoESalvar(@NonNull String documento) {
		
		Cliente cliente = buscarPorDocumento(documento);
		
		if (cliente == null) {
			
			ClienteApi clientApi = maxdataService.findClientMaxdataByCpfCgc(documento);
			
			if (clientApi == null) {
				
				throw new ClienteNaoEncontradoException(documento);
				
			}
			return gravaCliente(clientApi);
			
		} else {
			return cliente;
		}
		
	}
	
	@Transactional
	public Cliente gravaCliente(ClienteApi clienteApi) {
		
		Cliente cliente = toCliente(clienteApi);
		
		return clienteRepository.saveAndFlush(cliente);
		
	}
	
	private Cliente toCliente(ClienteApi clienteApi) {
		
		return Cliente.builder()
				.bairro(clienteApi.getClifatbairro())
				.cep(clienteApi.getClifatcep())
				.cidade(clienteApi.getClifatcidade())
				.codIbge(clienteApi.getClifatcidcodibge())
				.documento(clienteApi.getClicpfcgc())
				.endereco(clienteApi.getClifatend())
				.fantasia(clienteApi.getClifantasia())
				.id(clienteApi.getCliid())
				.inativo(clienteApi.getClidesativa() == 0 ? false : true)
				.numero(clienteApi.getClifatendnumero())
				.razao(clienteApi.getClinome())
				.tipo(clienteApi.getClitipo())
				.tipoCadastro(clienteApi.getClitipocad())
				.uf(clienteApi.getClifatuf())
				.build();
		
	}

}
