package br.com.developed.ideal.atendimento.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.developed.ideal.atendimento.api.assembler.MensagemModelAssembler;
import br.com.developed.ideal.atendimento.api.model.MensagemModel;
import br.com.developed.ideal.atendimento.api.model.MensagemResumoModel;
import br.com.developed.ideal.atendimento.api.model.input.MensagemInput;
import br.com.developed.ideal.atendimento.core.security.CheckSecurity;
import br.com.developed.ideal.atendimento.domain.repository.MensagemRepository;
import br.com.developed.ideal.atendimento.domain.service.MensagemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping(path = "/mensagens", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MensagemController {
	
	private final MensagemRepository mensagemRepository;
	
	private final MensagemService mensagemService;
	
	private final MensagemModelAssembler mensagemModelAssembler;
	
	@CheckSecurity.Mensagens.PermiteConsultar
	@GetMapping
	public Page<MensagemResumoModel> paginar(Pageable pageable) {
		
		return mensagemModelAssembler.toPageModel(mensagemRepository.findByDataExclusaoIsNull(pageable));
		
	}
	
	@CheckSecurity.Mensagens.PermiteConsultar
	@GetMapping("/{mensagemId}")
	public MensagemModel buscar(@PathVariable Long mensagemId) {
		
		return mensagemModelAssembler.toModel(mensagemService.buscarOuFalhar(mensagemId));
		
	}
	
	@CheckSecurity.Mensagens.PermiteCriar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MensagemModel criar(@Valid @RequestBody MensagemInput input) {
		
		return mensagemModelAssembler.toModel(mensagemService.criar(input));
		
	}
	
	@CheckSecurity.Mensagens.PermiteAlterar
	@PutMapping("/{mensagemId}")
	public MensagemModel alterar(
			@PathVariable Long mensagemId, 
			@Valid @RequestBody MensagemInput input) {
		
		return mensagemModelAssembler.toModel(mensagemService.alterar(mensagemId, input));
		
	}
	
	@CheckSecurity.Mensagens.PermiteExcluir
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{mensagemId}")
	public ResponseEntity<Void> excluir(@PathVariable Long mensagemId) {
		
		mensagemService.excluir(mensagemId);
		
		return ResponseEntity.noContent().build();
	}
	

}
