package br.com.developed.ideal.atendimento.core.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class ResourceServerConfig {
	
	private static final String[] WHITE_LIST_URLS = {"/autenticacao", "/home", 
			"/publico/**", "/publico/agendamento/**", "/flowable-modeler", "/flowable-modeler/**"};
	
	@Bean
	SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf(c -> c.disable())
			.cors(c -> c.disable())
			.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(config -> {
				config
				.requestMatchers(WHITE_LIST_URLS)
				.permitAll()
				.anyRequest().authenticated();
			})
			.httpBasic(c -> c.init(http))
			.oauth2ResourceServer(cust -> cust.jwt(c -> c.jwtAuthenticationConverter(jwtAuthenticationConverter())));
		
		return http.build();
		
	}
	
	private JwtAuthenticationConverter jwtAuthenticationConverter() {
		
		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
		
		converter.setJwtGrantedAuthoritiesConverter(jwt -> {
			
			List<String> authorities = jwt.getClaimAsStringList("authorities");
			
			if (authorities == null) {
				return Collections.emptyList();
			}
			
			JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
			
			Collection<GrantedAuthority> grantedAuthorities =  authoritiesConverter.convert(jwt);
			
			grantedAuthorities.addAll(authorities.stream().map(SimpleGrantedAuthority::new).toList());
			
			return grantedAuthorities;
			
		});
		
		return converter;
		
	}
	
	
	@Bean
	AuthenticationManager customAuthenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		
		return auth -> {
			
			String username = (String) auth.getPrincipal();
			String password = (String) auth.getCredentials();
						
			UserDetails user = userDetailsService.loadUserByUsername(username);
			
			if (!passwordEncoder.matches(password, user.getPassword())) {
				throw new BadCredentialsException("Usuário e/ou senha inválidos");
			}
			
			if (!user.isEnabled()) {
				throw new BadCredentialsException("O usuário não está ativo");
			}
			
			
			return new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
			
		};
		
	}

}
