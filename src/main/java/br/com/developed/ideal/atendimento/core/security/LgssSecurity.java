package br.com.developed.ideal.atendimento.core.security;

import org.springframework.stereotype.Component;

import br.com.developed.ideal.atendimento.domain.model.Usuario;
import br.com.developed.ideal.atendimento.domain.service.UsuarioService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class LgssSecurity {
	
	private UsuarioService usuarioService;
	
	public boolean permiteVisualizarCadastroUsuario(Long usuarioId) {
		
		Usuario usuario = usuarioService.getUsuarioLogado();
		
		return usuario.getId().equals(usuarioId);
		
	}

}
