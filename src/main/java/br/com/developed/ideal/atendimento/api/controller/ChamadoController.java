package br.com.developed.ideal.atendimento.api.controller;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.developed.ideal.atendimento.api.assembler.ChamadoModelAssembler;
import br.com.developed.ideal.atendimento.api.model.ChamadoModel;
import br.com.developed.ideal.atendimento.api.model.ChamadoResumoModel;
import br.com.developed.ideal.atendimento.api.model.DashBoardModel;
import br.com.developed.ideal.atendimento.api.model.input.EncerrarChamadoInput;
import br.com.developed.ideal.atendimento.api.model.input.NovoChamadoInput;
import br.com.developed.ideal.atendimento.api.model.input.SalvarChamadoInput;
import br.com.developed.ideal.atendimento.core.security.CheckSecurity;
import br.com.developed.ideal.atendimento.domain.filter.ChamadoFilter;
import br.com.developed.ideal.atendimento.domain.repository.ChamadoRepository;
import br.com.developed.ideal.atendimento.domain.service.ChamadoService;
import br.com.developed.ideal.atendimento.domain.specs.ChamadoSpecs;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/chamados", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ChamadoController {
	
	private final ChamadoRepository chamadoRepository;
	
	private final ChamadoService chamadoService;
	
	private final ChamadoModelAssembler chamadoModelAssembler;
	
	@CheckSecurity.Autenticado
	@GetMapping
	public Page<ChamadoResumoModel> listar(ChamadoFilter filter, Pageable pageable) {
		
		return chamadoModelAssembler
				.toPageModel(chamadoRepository
						.findAll(ChamadoSpecs.usandoFiltro(filter), pageable));
		
	}
	

	
	@CheckSecurity.Autenticado
	@GetMapping("/dashboard")
	public DashBoardModel dashBoard() {
		return chamadoService.carregaDadosDashBoard();
	}
	
	@CheckSecurity.Autenticado
	@GetMapping("/para-tratar")
	public Page<ChamadoResumoModel> chamadosParaTratar(Pageable pageable) {
		
		return chamadoModelAssembler
				.toPageModel(chamadoService.carregaAbertosFila(pageable));
		
	}
	
	
	@CheckSecurity.Autenticado
	@GetMapping("/para-tratar/comigo")
	public Page<ChamadoResumoModel> chamadosParaTratarComigo(Pageable pageable) {
		
		return chamadoModelAssembler
				.toPageModel(chamadoService.carregarEmTratamentoUsuarioLogado(pageable));
		
	}
	
	@CheckSecurity.Autenticado
	@GetMapping("/{chamadoId}")
	public ChamadoModel buscar(@PathVariable Long chamadoId) {
		return chamadoModelAssembler.toModel(chamadoService.buscarOuFalhar(chamadoId));
	}
	
	@CheckSecurity.Autenticado
	@GetMapping("/{chamadoId}/anexos/{anexoId}/download")
	public ResponseEntity<Resource> downloadAnexo(@PathVariable Long chamadoId, @PathVariable Long anexoId) {
		
		return chamadoService.getResourceAnexo(chamadoId, anexoId); 
		
		
	}
	
	
	@CheckSecurity.Autenticado
	@PutMapping("/{chamadoId}/iniciar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void iniciarChamado(@PathVariable Long chamadoId) {
		
		chamadoService.iniciarTratamentoChamado(chamadoId);
		
	}
	
	@CheckSecurity.Autenticado
	@PutMapping("/{chamadoId}/encerrar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void encerrarChamado(
			@PathVariable Long chamadoId, 
			@Valid @RequestBody EncerrarChamadoInput input) {
		
		chamadoService.encerrarChamado(chamadoId, input);
		
	}
	
	@CheckSecurity.Autenticado
	@PutMapping("{chamadoId}/cancelar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelarChamado(
			@PathVariable Long chamadoId, 
			@Valid @RequestBody EncerrarChamadoInput input) {
		
		chamadoService.cancelarChamado(chamadoId, input);
		
	}
	
	@CheckSecurity.Autenticado
	@PatchMapping("/{chamadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void salvarChamado(@PathVariable Long chamadoId, @RequestBody SalvarChamadoInput input) {
		
		chamadoService.salvarChamado(chamadoId, input);
		
	}
	
	@CheckSecurity.Autenticado
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ChamadoModel criar(@RequestPart("dados") NovoChamadoInput input,
	        @RequestPart(value = "anexos", required = false)
	        List<MultipartFile> anexos) {
		
		return chamadoModelAssembler.toModel(chamadoService.criar(input, anexos));
	}
	
	@CheckSecurity.Autenticado
	@PutMapping("/{chamadoId}/usuario/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atribuirChamado(@PathVariable Long chamadoId, @PathVariable Long usuarioId) {
		
		chamadoService.atribuirUsuario(chamadoId, usuarioId);
		
	}

}
