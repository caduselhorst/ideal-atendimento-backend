package br.com.developed.ideal.atendimento.core.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.developed.ideal.atendimento.domain.model.Usuario;
import br.com.developed.ideal.atendimento.domain.repository.UsuarioRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		
		Usuario usuario = usuarioRepository.findByLoginIgnoreCase(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o login informado"));
				
		UserDetails user = new User(usuario.getLogin(), usuario.getSenha(), !usuario.isInativo(), true, true, true,getAuthorities(usuario));
		
				
		return user;
	}
	
	private Collection<GrantedAuthority> getAuthorities(Usuario usuario) {
		
		Collection<GrantedAuthority> authorities = usuario.getPerfis().stream()
				.flatMap(perfil -> perfil.getPermissoes().stream())
				.map(permissao -> new SimpleGrantedAuthority(permissao.getNome().toUpperCase()))
				.collect(Collectors.toSet()); 
		
		return authorities;
	}

}
