package br.com.developed.ideal.atendimento.domain.comunication.listener;

import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import br.com.developed.ideal.atendimento.domain.comunication.event.AtribuirChamadoEvent;
import br.com.developed.ideal.atendimento.domain.model.MensagemBp;
import br.com.developed.ideal.atendimento.domain.service.MensagemBpService;
import br.com.developed.ideal.atendimento.domain.service.WppConnectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AtribuirChamadoListener {
	
	private final WppConnectService wppConnectService;
	private final MensagemBpService mensagemBpService;
	private static final String CHAVE_MENSAGEM = "sendNotificacaoChamadoAtribuido";
	
	@Async
	@EventListener
	public void processar(AtribuirChamadoEvent event) {
		
		if (event.usuarioAtribuido().getHabilitaNotificacaoWhatsapp() && event.usuarioAtribuido().getFone() != null && !event.usuarioAtribuido().getFone().isBlank() ) {
			
			MensagemBp mensagemBp = mensagemBpService.buscarOuFalharPorChave(CHAVE_MENSAGEM);
			
			Map<String, Object> vars = Map.of(
				"phone", "55" + formatNumber(event.usuarioAtribuido().getFone()),
				"chamadoId", event.chamado().getId(),
				"usuarioAtribuidor", event.usuarioAtribuidor().getNome()
			);
			
			String payload = StringSubstitutor.replace(mensagemBp.getPayload(), vars);
			
			try {
				
				wppConnectService.sendMessage(payload, mensagemBp.getTipo());
				
			} catch (Exception e) {
				log.error("Não foi possivel enviar a notificação", e);
			}
			
		}
		
		
	}
	
	private String formatNumber(String numero) {
		
		return numero.replace("(", "")
				.replace(")", "")
				.replace("-", "")
				.replace(".", "")
				.replace(" ", "");
	}

}
