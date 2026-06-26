package br.com.developed.ideal.atendimento.domain.comunication.listener;

import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import br.com.developed.ideal.atendimento.domain.comunication.event.ChamadoEmAtendimentoEvent;
import br.com.developed.ideal.atendimento.domain.model.MensagemBp;
import br.com.developed.ideal.atendimento.domain.model.TipoMensagemBP;
import br.com.developed.ideal.atendimento.domain.service.MensagemBpService;
import br.com.developed.ideal.atendimento.domain.service.WppConnectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChamadoEmAtendimentoListener {
	
	private final MensagemBpService mensagemBpService;
	private final WppConnectService wppConnectService;
	
	private static final String CHAVE_MENSAGEM_NOTIFICACAO_CHAMADO_EM_TRATAMENTO = "sendNotificacaoChamadoEmAtendimento";
	
	@Async
	@EventListener
	public void processar(ChamadoEmAtendimentoEvent event) {
		
		Map<String, Object> vars = Map.of(
					"phone", event.telefone(),
					"nroChamado", event.chamadoId(),
					"usuarioTratamento", event.nomeUsuario()
				);
		
		MensagemBp mensagemBp = mensagemBpService.buscarOuFalharPorChave(
				CHAVE_MENSAGEM_NOTIFICACAO_CHAMADO_EM_TRATAMENTO);
		
		String payload = StringSubstitutor.replace(mensagemBp.getPayload(), vars);
		
		try {
			
			wppConnectService.sendMessage(payload, TipoMensagemBP.TEXTO);
			
		} catch (Exception e) {
			log.error("Erro ao enviar notificação", e);
		}
		
	}

}
