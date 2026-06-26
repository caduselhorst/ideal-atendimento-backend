package br.com.developed.ideal.atendimento.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.developed.ideal.atendimento.api.assembler.MensagemBpModelAssembler;
import br.com.developed.ideal.atendimento.api.model.MensagemBpModel;
import br.com.developed.ideal.atendimento.api.model.input.MensagemBpInput;
import br.com.developed.ideal.atendimento.core.security.CheckSecurity;
import br.com.developed.ideal.atendimento.domain.repository.MensagemBpRepository;
import br.com.developed.ideal.atendimento.domain.service.MensagemBpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/mensagens-bp", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MensagemBpController {
	
	private final MensagemBpRepository mensagemBpRepository;
	private final MensagemBpService mensagemBpService;
	
	private final MensagemBpModelAssembler mensagemBpModelAssembler;
	
	@CheckSecurity.Mensagens.PermiteConsultar
	@GetMapping
	public Page<MensagemBpModel> paginar(Pageable pageable) {
		
		return mensagemBpModelAssembler
				.toPageModel(mensagemBpRepository
						.findAll(pageable));
		
	}
	
	@CheckSecurity.Mensagens.PermiteConsultar
	@GetMapping("/{mensagemId}")
	public MensagemBpModel buscar(
			@PathVariable Long mensagemId) {
		
		return mensagemBpModelAssembler
				.toModel(mensagemBpService
						.buscarOuFalharPorId(mensagemId));
		
	}
	
	@CheckSecurity.Mensagens.PermiteCriar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MensagemBpModel criar(@Valid @RequestBody MensagemBpInput input) {
		
		return mensagemBpModelAssembler
				.toModel(mensagemBpService.criar(input));
		
	}
	
	@CheckSecurity.Mensagens.PermiteAlterar
	@PutMapping("/{mensagemId}")
	public MensagemBpModel alterar(@PathVariable Long mensagemId,
			@Valid @RequestBody MensagemBpInput input) {
		
		return mensagemBpModelAssembler
				.toModel(mensagemBpService.alterar(mensagemId, input));
		
	}
	
	@CheckSecurity.Mensagens.PermiteExcluir
	@DeleteMapping("/{mensagemId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long mensagemId) {
		mensagemBpService.excluir(mensagemId);
	}
	

}
