package br.com.developed.ideal.atendimento.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.developed.ideal.atendimento.domain.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	public Optional<Usuario> findByLoginIgnoreCase(String login);
	public Page<Usuario> findByNomeContainingIgnoreCase(Pageable pageable, String nome);
	public boolean existsByLoginIgnoreCase(String login);
	public List<Usuario> findByInativoOrderByNome(Boolean inativo);
	public List<Usuario> findByHabilitaNotificacaoWhatsapp(Boolean habilitaNotificacao);
	
}
