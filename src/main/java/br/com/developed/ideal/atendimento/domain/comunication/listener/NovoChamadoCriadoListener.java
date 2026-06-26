package br.com.developed.ideal.atendimento.domain.comunication.listener;

import java.util.List;
import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import br.com.developed.ideal.atendimento.domain.comunication.event.NovoChamadoCriadoEvent;
import br.com.developed.ideal.atendimento.domain.model.MensagemBp;
import br.com.developed.ideal.atendimento.domain.model.Usuario;
import br.com.developed.ideal.atendimento.domain.repository.UsuarioRepository;
import br.com.developed.ideal.atendimento.domain.service.MensagemBpService;
import br.com.developed.ideal.atendimento.domain.service.WppConnectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class NovoChamadoCriadoListener {
	
	private final MensagemBpService mensagemBpService;
	private final WppConnectService wppConnectService;
	private final UsuarioRepository usuarioRepository;
	
	private static final String CHAVE_MENSAGEM_NOTIFICACAO_NOVO_CHAMADO = "sendNotificacaoNovoChamado";
	private static final String CHAVE_MENSAGEM_NOTIFICACAO_NOVO_CHAMADO_WEB_CLIENTE = "sendNotificacaoNovoChamadoWebCliente";
	
	@Async
	@EventListener
	public void processar(NovoChamadoCriadoEvent event) {
		
		try {
			enviaMsgAnalistas(event);
		} catch( Exception e) {
			log.error("Erro ao enviar notificação aos analistas", e);
		}
		
		if (event.web()) {
			
			try {
				enviaMsgCliente(event);
			} catch (Exception e) {
				log.error("Erro ao enviar notificação ao cliente");
			}
			
		}
		
	}
	
	private void enviaMsgAnalistas(NovoChamadoCriadoEvent event) {
		
		List<Usuario> usuarios = usuarioRepository.findByHabilitaNotificacaoWhatsapp(true);
		
		MensagemBp mensagem = mensagemBpService.buscarOuFalharPorChave(CHAVE_MENSAGEM_NOTIFICACAO_NOVO_CHAMADO);
		
		
		usuarios.forEach(u -> {
			
			if (u.getFone() != null && !u.getFone().isEmpty()) {
				
				String aux = u.getFone()
						.replace(" ", "")
						.replace("(", "")
						.replace(")", "")
						.replace("-", "")
						.replace(".", "");
				
				Map<String, Object> vars = Map.of(
							"phone", aux,
							"chamadoId", event.chamadoId(),
							"razao", event.razao(),
							"fantasia", event.fantasia()
						);
				
				String payload = StringSubstitutor.replace(mensagem.getPayload(), vars);
				
				wppConnectService.sendMessage(payload, mensagem.getTipo());
				
			}
			
		});
		
	}
	
	private void enviaMsgCliente(NovoChamadoCriadoEvent event) {
		
		MensagemBp mensagem = mensagemBpService.buscarOuFalharPorChave(CHAVE_MENSAGEM_NOTIFICACAO_NOVO_CHAMADO_WEB_CLIENTE);
		
		String aux = event.contatoCliente()
				.replace(" ", "")
				.replace("(", "")
				.replace(")", "")
				.replace("-", "")
				.replace(".", "");
		
		Map<String, Object> vars = Map.of(
				"phone", aux,
				"chamadoId", event.chamadoId(),
				"razao", event.razao(),
				"fantasia", event.fantasia(),
				"assunto", event.assunto()
			);
		
		String payload = StringSubstitutor.replace(mensagem.getPayload(), vars);
		
		wppConnectService.sendMessage(payload, mensagem.getTipo());
	}

}
