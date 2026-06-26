package br.com.developed.ideal.atendimento.flowable.delegators;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.developed.ideal.atendimento.api.model.input.AnexoInput;
import br.com.developed.ideal.atendimento.api.model.input.AnexosInput;
import br.com.developed.ideal.atendimento.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AnexarArquivoDelegate implements JavaDelegate {
	
	private final ObjectMapper objectMapper;

	@Override
	public void execute(DelegateExecution execution) {
		// TODO Auto-generated method stub
		
		try {
			
			log.debug("Iniciando AnexarArquivoDelegate");
			
			String jsonAnexos = (String) execution.getVariable("anexos");
			String nomeArquivo = (String) execution.getVariable("nomeArquivo");
			String tipoArquivo = (String) execution.getVariable("tipoArquivo");
			String conteudoArquivo = (String) execution.getVariable("conteudoArquivo");
			
			if (nomeArquivo == null) {
				
				nomeArquivo = LocalDateTime.now()
						.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + 
						AppUtils.getExtensaoArquivoPeloMimeType(tipoArquivo);
				
			}
			
			AnexoInput anexo = AnexoInput.builder()
				.conteudoArquivo(conteudoArquivo)
				.nomeArquivo(nomeArquivo)
				.tipoArquivo(tipoArquivo)
				.build();
			
			AnexosInput anexos = null;
			
			if (jsonAnexos != null) {
				
				anexos = objectMapper.readValue(jsonAnexos, AnexosInput.class);
				
				
			} else {
				
				anexos = new AnexosInput();
				
			}
			
			if (anexos.getAnexos() == null) {
				anexos.setAnexos(new ArrayList<>());
			}
			
			anexos.adicionarAnexo(anexo);
			
			execution.setVariable("anexos", objectMapper.writeValueAsString(anexos));
			
						
			log.debug("AnexarArquivoDelegate finalizado");
			
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
	}
	
	
}
