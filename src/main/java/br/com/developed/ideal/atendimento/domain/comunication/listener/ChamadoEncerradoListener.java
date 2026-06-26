package br.com.developed.ideal.atendimento.domain.comunication.listener;

import java.util.Map;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import br.com.developed.ideal.atendimento.domain.comunication.event.ChamadoEncerradoEvent;
import br.com.developed.ideal.atendimento.domain.model.MensagemBp;
import br.com.developed.ideal.atendimento.domain.model.TipoMensagemBP;
import br.com.developed.ideal.atendimento.domain.service.MensagemBpService;
import br.com.developed.ideal.atendimento.domain.service.WppConnectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChamadoEncerradoListener {
	
	private final MensagemBpService mensagemBpService;
	private final WppConnectService wppConnectService;
	
	private static final String CHAVE_MENSAGEM_NOTIFICACAO_CHAMADO_ENCERRADO = "sendNotificacaoChamadoEncerrado";
	private static final String CHAVE_MENSAGEM_NOTIFICACAO_CHAMADO_CANCELADO = "sendNotificacaoChamadoCancelado";
	
	@Async
	@EventListener
	public void processar(ChamadoEncerradoEvent event) {
		
		try {
		
			Map<String, Object> vars = Map.of(
					"phone", event.telefone(),
					"nroChamado", event.chamadoId(),
					"usuario", event.usuario(),
					"descricaoFechamento", StringEscapeUtils.escapeJava(event.descricaoFechamento())
				);
		
			MensagemBp mensagemBp = mensagemBpService.buscarOuFalharPorChave(
					!event.cancelado() ? CHAVE_MENSAGEM_NOTIFICACAO_CHAMADO_ENCERRADO :  CHAVE_MENSAGEM_NOTIFICACAO_CHAMADO_CANCELADO);
			
			String payload = StringSubstitutor.replace(mensagemBp.getPayload(), vars);
			
			wppConnectService.sendMessage(payload, TipoMensagemBP.TEXTO);
		} catch (Exception e) {
			log.error("Erro ao enviar a notificação", e);
		}
		
	}

}
