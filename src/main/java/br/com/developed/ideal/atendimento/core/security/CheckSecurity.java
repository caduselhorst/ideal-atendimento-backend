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
		
		@PreAuthorize("isAuthenticated and hasAuthority('PERMITE_LER_PERMISSOES')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar {
		}
		
	}
	
	public @interface Perfis {
		
		@PreAuthorize("isAuthenticated and hasAuthority('PERMITE_CRIAR_PERFIS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteCriar {
		}
		
		@PreAuthorize("isAuthenticated and hasAuthority('PERMITE_ALTERAR_PERFIS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteAlterar {
		}
		
		@PreAuthorize("isAuthenticated and hasAuthority('PERMITE_EXCLUIR_PERFIS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteExcluir {
		}

		@PreAuthorize("isAuthenticated and hasAuthority('PERMITE_LER_PERFIS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteConsultar {
		}
		
	}
	
	public @interface Usuarios {
		
		@PreAuthorize("isAuthenticated and hasAuthority('PERMITE_CRIAR_USUARIOS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteCriar {
		}
		
		@PreAuthorize("isAuthenticated and hasAuthority('PERMITE_ALTERAR_USUARIOS') or @pgeSecurity.permiteVisualizarCadastroUsuario(#usuarioId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteAlterar {
		}
		
		@PreAuthorize("isAuthenticated and hasAuthority('PERMITE_EXCLUIR_USUARIOS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteExcluir {
		}

		@PreAuthorize("isAuthenticated and hasAuthority('PERMITE_LER_USUARIOS') or @pgeSecurity.permiteVisualizarCadastroUsuario(#usuarioId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteConsultar {
		}
		
	}
	
	
	public @interface GruposUsuario {
		
		@PreAuthorize("isAuthenticated and hasAuthority('PERMITE_CRIAR_GRUPOS_USUARIO')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteCriar {
		}
		
		@PreAuthorize("isAuthenticated and hasAuthority('PERMITE_ALTERAR_GRUPOS_USUARIO')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteAlterar {
		}
		
		@PreAuthorize("isAuthenticated and hasAuthority('PERMITE_EXCLUIR_GRUPOS_USUARIO')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteExcluir {
		}

		@PreAuthorize("isAuthenticated and hasAuthority('PERMITE_LER_GRUPOS_USUARIO')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteConsultar {
		}
		
	}
	
	public @interface Processos {
		
		@PreAuthorize("isAuthenticated and hasAuthority('PERMITE_CRIAR_PROCESSOS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteCriar {
		}
		
		@PreAuthorize("isAuthenticated and hasAuthority('PERMITE_ALTERAR_PROCESSOS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteAlterar {
		}
		
		@PreAuthorize("isAuthenticated and hasAuthority('PERMITE_EXCLUIR_PROCESSOS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteExcluir {
		}

		@PreAuthorize("isAuthenticated and hasAuthority('PERMITE_LER_PROCESSOS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteConsultar {
		}
		
	}
	
	public @interface Formularios {
		
		@PreAuthorize("isAuthenticated and hasAuthority('PERMITE_CRIAR_FORMULARIOS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteCriar {
		}
		
		@PreAuthorize("isAuthenticated and hasAuthority('PERMITE_ALTERAR_FORMULARIOS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteAlterar {
		}
		
		@PreAuthorize("isAuthenticated and hasAuthority('PERMITE_EXCLUIR_FORMULARIOS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteExcluir {
		}

		@PreAuthorize("isAuthenticated and hasAuthority('PERMITE_LER_FORMULARIOS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PermiteConsultar {
		}
		
	}

}
