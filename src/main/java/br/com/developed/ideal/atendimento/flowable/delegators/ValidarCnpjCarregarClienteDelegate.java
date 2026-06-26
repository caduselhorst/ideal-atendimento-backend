package br.com.developed.ideal.atendimento.flowable.delegators;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.developed.ideal.atendimento.domain.model.Cliente;
import br.com.developed.ideal.atendimento.domain.model.maxdata.ClienteApi;
import br.com.developed.ideal.atendimento.domain.service.ClienteService;
import br.com.developed.ideal.atendimento.domain.service.MaxdataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ValidarCnpjCarregarClienteDelegate implements JavaDelegate {
	
	private final ClienteService clienteService;
	private final MaxdataService maxdataService;
	private final ObjectMapper mapper;
	
	@Override
	public void execute(DelegateExecution execution) {
		
		try {
			
			String cnpj = (String) execution.getVariable("cnpj");
			
			
			
			if (cnpj == null) {
				throw new RuntimeException("CNPJ não informado ou não configurado no workflow");
			}
			
			cnpj = cnpj.replace(".", "")
					.replace("-", "")
					.replace("/", "")
					.replace(" ", "");
			
			CNPJValidator validator = new CNPJValidator();
			if (!validator.invalidMessagesFor(cnpj).isEmpty()) {
				
				execution.setVariable("cnpjInvalido", true);
				execution.setVariable("clienteEncontrado", false);
				
			} else {
				
				Cliente cliente = clienteService.buscarPorDocumento(cnpj);
				
				if(cliente == null) {
					
					ClienteApi clienteMaxdata = maxdataService.findClientMaxdataByCpfCgc(cnpj);
					
					if (clienteMaxdata == null) {
						
						execution.setVariable("clienteEncontrado", false);
						execution.setVariable("cnpjInvalido", false);
						
					} else {
						
						cliente = clienteService.gravaCliente(clienteMaxdata);
						execution.setVariable("cliente", mapper.writeValueAsString(cliente));
						execution.setVariable("clienteEncontrado", true);
						
					}
					
				} else {
					
					execution.setVariable("clienteEncontrado", true);
					execution.setVariable("cnpjInvalido", false);
					execution.setVariable("cliente", mapper.writeValueAsString(cliente));
					
				}
				
				
			}
			
			
		} catch (JsonProcessingException e) {
			
			log.error("Ocorreu um erro ao converter o objeto em json", e);
			throw new RuntimeException(e);
			
		}
		
	}

}
