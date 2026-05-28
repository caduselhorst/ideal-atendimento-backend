package br.com.developed.ideal.atendimento.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.developed.ideal.atendimento.api.model.LoginModel;
import br.com.developed.ideal.atendimento.domain.model.Usuario;
import br.com.developed.ideal.atendimento.domain.repository.UsuarioRepository;


@RestController
public class LoginController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	@PostMapping("/autenticacao")
	public LoginModel autentica(
			@RequestParam(required = true) String username,
			@RequestParam(required = true) String password) {
		
		
		
		Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		
		Usuario usuario = usuarioRepository.findByLoginIgnoreCase(auth.getName()).orElseThrow();
		
		String token = jwtHelper.createJwtForUsuario(usuario);
		
		LoginModel loginModel = new LoginModel();
		loginModel.setAccess_token(token);
		
		return loginModel;
		
	}

}
