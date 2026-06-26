package br.com.developed.ideal.atendimento.core.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {
	
	@PreAuthorize("isAuthenticated")
	@Retention(RUNTIME)
	@Target(METHOD)
	public @interface Autenticado {
	}
	
	public @interface Permissoes {
		
		@PreAuthorize("isAuthenticated and hasAuthority('PERMISSOES_PERMITE_LER')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar {
		}
		
	}
	
	public @interface Perfis {
		
		@PreAuthorize("isAuthenticated and hasAuthority('PERFIS_PERMITE_CRIAR')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteCriar {
		}
		
		@PreAuthorize("isAuthenticated and hasAuthority('PERFIS_PERMITE_ALTERAR')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteAlterar {
		}
		
		@PreAuthorize("isAuthenticated and hasAuthority('PERFIS_PERMITE_EXCLUIR')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteExcluir {
		}

		@PreAuthorize("isAuthenticated and hasAuthority('PERFIS_PERMITE_LER')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteConsultar {
		}
		
	}
	
	public @interface Usuarios {
		
		@PreAuthorize("isAuthenticated and hasAuthority('USUARIOS_PERMITE_CRIAR')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteCriar {
		}
		
		@PreAuthorize("isAuthenticated and hasAuthority('USUARIOS_PERMITE_ALTERAR') or @lgssSecurity.permiteVisualizarCadastroUsuario(#usuarioId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteAlterar {
		}
		
		@PreAuthorize("isAuthenticated and hasAuthority('USUSARIOS_PERMITE_EXCLUIR')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteExcluir {
		}

		@PreAuthorize("isAuthenticated and hasAuthority('USUARIOS_PERMITE_LER') or @lgssSecurity.permiteVisualizarCadastroUsuario(#usuarioId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteConsultar {
		}
		
	}
	
	
	public @interface GruposUsuario {
		
		@PreAuthorize("isAuthenticated and hasAuthority('GRUPOS_USUARIO_PERMITE_CRIAR')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteCriar {
		}
		
		@PreAuthorize("isAuthenticated and hasAuthority('GRUPOS_USUARIO_PERMITE_ALTERAR')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteAlterar {
		}
		
		@PreAuthorize("isAuthenticated and hasAuthority('GRUPOS_USUARIO_PERMITE_EXCLUIR')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteExcluir {
		}

		@PreAuthorize("isAuthenticated and hasAuthority('GRUPOS_USUARIO_PERMITE_LER')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteConsultar {
		}
		
	}
	
	public @interface Mensagens {
		
		@PreAuthorize("isAuthenticated and hasAuthority('MENSAGENS_PERMITE_CRIAR')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteCriar {
		}
		
		@PreAuthorize("isAuthenticated and hasAuthority('MENSAGENS_PERMITE_ALTERAR')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteAlterar {
		}
		
		@PreAuthorize("isAuthenticated and hasAuthority('MENSAGENS_PERMITE_EXCLUIR')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteExcluir {
		}

		@PreAuthorize("isAuthenticated and hasAuthority('MENSAGENS_PERMITE_LER')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteConsultar {
		}
		
	}
	


}
