package br.com.developed.ideal.atendimento.core.security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.developed.ideal.atendimento.domain.model.Usuario;

@Component
public class JwtHelper {

	@Autowired
	private RSAPrivateKey privateKey;
	
	@Autowired
	private RSAPublicKey publicKey;
	
	@Autowired
	private JwtKeyStoreProperties jwtKeyStoreProperties;
	
	private Calendar getCalendar() {
		
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, jwtKeyStoreProperties.getValidityInSeconds());
		
		return calendar;
	}
	
	private JWTCreator.Builder getJWTCreateBuilder(String subject) {
		
		return JWT.create().withSubject(subject);
		
	}
	
	public String createJwtForUsuario(Usuario usuario) {
		
		JWTCreator.Builder jwtBuilder = getJWTCreateBuilder(usuario.getLogin());
		
		jwtBuilder.withClaim("usuarioId", String.valueOf(usuario.getId()));
		jwtBuilder.withClaim("nome", usuario.getNome());
		jwtBuilder.withClaim("email", usuario.getEmail());
		jwtBuilder.withClaim("fone", usuario.getFone());
		jwtBuilder.withClaim("gruposBpms", usuario.getGrupos()
				.stream()
				.map(grupo -> grupo.getIdentificadorbpms())
				.collect(Collectors.joining(",")));
		jwtBuilder.withClaim("authorities", usuario.getPerfis()
				.stream()
				.flatMap(perfil -> perfil.getPermissoes().stream())
				.map(permissao -> permissao.getNome())
				.toList());
		
		return jwtBuilder
				.withNotBefore(new Date())
				.withExpiresAt(getCalendar().getTime())
				.sign(Algorithm.RSA256(publicKey, privateKey));
		
	}
	
	
	public String createJwtForClaims(String subject, Map<String, Object> claims) {
		
		JWTCreator.Builder jwtBuilder = getJWTCreateBuilder(subject); 
		
		// Add claims
		claims.forEach((key, value) -> jwtBuilder.withClaim(key, value.toString()));
		
		// Add expiredAt and etc
		return jwtBuilder
				.withNotBefore(new Date())
				.withExpiresAt(getCalendar().getTime())
				.sign(Algorithm.RSA256(publicKey, privateKey));
	}
}
