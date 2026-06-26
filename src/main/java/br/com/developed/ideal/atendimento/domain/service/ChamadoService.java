package br.com.developed.ideal.atendimento.domain.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import br.com.developed.ideal.atendimento.api.model.ContadorChamado;
import br.com.developed.ideal.atendimento.api.model.DashBoardModel;
import br.com.developed.ideal.atendimento.api.model.input.AnexoInput;
import br.com.developed.ideal.atendimento.api.model.input.AnexosInput;
import br.com.developed.ideal.atendimento.api.model.input.ChamadoInput;
import br.com.developed.ideal.atendimento.api.model.input.EncerrarChamadoInput;
import br.com.developed.ideal.atendimento.api.model.input.NovoChamadoInput;
import br.com.developed.ideal.atendimento.api.model.input.SalvarChamadoInput;
import br.com.developed.ideal.atendimento.domain.comunication.event.AtribuirChamadoEvent;
import br.com.developed.ideal.atendimento.domain.comunication.event.ChamadoEmAtendimentoEvent;
import br.com.developed.ideal.atendimento.domain.comunication.event.ChamadoEncerradoEvent;
import br.com.developed.ideal.atendimento.domain.comunication.event.NovoChamadoCriadoEvent;
import br.com.developed.ideal.atendimento.domain.exception.ChamadoNaoEncontradoException;
import br.com.developed.ideal.atendimento.domain.exception.NegocioException;
import br.com.developed.ideal.atendimento.domain.model.Chamado;
import br.com.developed.ideal.atendimento.domain.model.ChamadoAnexo;
import br.com.developed.ideal.atendimento.domain.model.Cliente;
import br.com.developed.ideal.atendimento.domain.model.StatusChamado;
import br.com.developed.ideal.atendimento.domain.model.Usuario;
import br.com.developed.ideal.atendimento.domain.projection.AnalistaChamadoProjection;
import br.com.developed.ideal.atendimento.domain.projection.ClienteChamadoProjetion;
import br.com.developed.ideal.atendimento.domain.projection.DataChamadoProjection;
import br.com.developed.ideal.atendimento.domain.repository.ChamadoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class ChamadoService {
	
	private final ChamadoRepository chamadoRepository;
	private final StorageService storageService;
	private final ClienteService clienteService;
	private final UsuarioService usuarioService;
	
	private final ApplicationEventPublisher publisher;
	
	public Chamado buscarPorIdECnpj(Long chamadoId, String cnpj) {
		
		Chamado chamado = buscarOuFalhar(chamadoId);
		
		if (!chamado.getCliente().getDocumento().equals(cnpj)) {
			throw new ChamadoNaoEncontradoException(chamadoId, cnpj);
		}
		
		return chamado;
		
	}
	
	public Chamado buscarOuFalhar(Long chamadoId) {
		
		return chamadoRepository.findById(chamadoId)
				.orElseThrow(() -> new ChamadoNaoEncontradoException(chamadoId));
		
	}
	
	public Page<Chamado> carregaAbertosFila(Pageable pageable) {
		
		return chamadoRepository
				.findByStatusChamadoAndUsuarioTratamento(StatusChamado.AGUARDANDO, null, pageable);
		
	}
	
	public Page<Chamado> carregarEmTratamento(Usuario usuario, Pageable pageable) {
		
		return chamadoRepository
				.findByStatusChamadoAndUsuarioTratamento(StatusChamado.EM_ATENDIMENTO, usuario, pageable);
		
	}
	
	public Page<Chamado> carregarEmTratamentoUsuarioLogado(Pageable pageable) {
		
		Usuario usuarioLogado = usuarioService.getUsuarioLogado();
		
		return carregarEmTratamento(usuarioLogado, pageable);
		
	}
	
	public ContadorChamado contaChamadosUsuarioLogado() {
		
		Usuario usuarioLogadp = usuarioService.getUsuarioLogado();
		
		Long quantidade= chamadoRepository
				.countByStatusChamadoAndUsuarioTratamento(StatusChamado.EM_ATENDIMENTO, usuarioLogadp);
		
		return ContadorChamado.builder()
				.quantidade(quantidade)
				.build();
		
	}
	
	@Cacheable(
		value = "dashboard",
		key = "@usuarioService.getUsuarioLogado().id"
	)
	public DashBoardModel carregaDadosDashBoard() {
		
		Usuario usuarioLogado = usuarioService.getUsuarioLogado();
		
		Long quantidadeChamadosFila = chamadoRepository
				.countByStatusChamadoAndUsuarioTratamento(StatusChamado.AGUARDANDO, null);
		
		Long quantidadeChamadosComigo =  chamadoRepository
				.countByStatusChamadoAndUsuarioTratamento(StatusChamado.EM_ATENDIMENTO, usuarioLogado);
		
		List<ClienteChamadoProjetion> clientesChamado = chamadoRepository.contaChamadosCliente(
			    OffsetDateTime.now().minusMonths(3),
			    PageRequest.of(0, 5)
			);
		
		List<AnalistaChamadoProjection> analistasChamado = chamadoRepository.contaChamadosAnalista(
			    OffsetDateTime.now().minusMonths(3),
			    PageRequest.of(0, 5)
			);
		
		List<DataChamadoProjection> datasChamado = chamadoRepository
				.contaChamadosData(OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.of("-03:00")).minusDays(30));
		
		return DashBoardModel.builder()
				.clientesChamados(clientesChamado)
				.quantidadeChamadosComigo(quantidadeChamadosComigo)
				.quantidadeChamadosFila(quantidadeChamadosFila)
				.analistasChamados(analistasChamado)
				.datasChamados(datasChamado)
				.build();
		
	}
	
	public ResponseEntity<Resource> getResourceAnexo(Long chamadoId, Long anexoId) {
		
		Chamado chamado = buscarOuFalhar(chamadoId);
		
		if (chamado.getAnexos() == null || chamado.getAnexos().isEmpty()) {
			throw new NegocioException("O anexo com ID " + anexoId + " não está vinculado ao chamado " + chamadoId);
		}
		
		
		List<ChamadoAnexo> filtrado = chamado
				.getAnexos()
				.stream()
				.filter(a -> a.getId().equals(anexoId))
				.toList();
		
		if (filtrado.isEmpty()) {
			throw new NegocioException("O anexo com ID " + anexoId + " não está vinculado ao chamado " + chamadoId);
		}
		
		ChamadoAnexo anexo = filtrado.getFirst();
		
		FileSystemResource resource = new FileSystemResource(storageService.recuperar(anexo.getCaminho()));
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(anexo.getTipo()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + anexo.getNome() + "\"")
				.body(resource);
		
	}
	
	@Transactional
	public void iniciarTratamentoChamado(Long chamadoId) {
		
		Usuario usuario = usuarioService.getUsuarioLogado();
		
		Chamado chamado = buscarOuFalhar(chamadoId);
		
		if (StatusChamado.FINALIZADO.equals(chamado.getStatusChamado()) || StatusChamado.CANCELADO.equals(chamado.getStatusChamado())) {
			throw new NegocioException("Não é possível alterar chamados CANCELADOS ou FINALIZADOS");
		}
		
		if(chamado.getStatusChamado().equals(StatusChamado.EM_ATENDIMENTO)) {
			throw new NegocioException("Chamado já está em atendimento");
		}
		
		chamado.setUsuarioTratamento(usuario);
		chamado.setStatusChamado(StatusChamado.EM_ATENDIMENTO);
		
		publisher.publishEvent(new ChamadoEmAtendimentoEvent(
				chamado.getId(), chamado.getFoneAbertura(), chamado.getUsuarioTratamento().getNome()));
		
		chamadoRepository.save(chamado);
		
	}
	
	
	
	@Transactional
	public Chamado criar (@Valid ChamadoInput input, @Valid AnexosInput anexos) {
		
		Chamado chamado = criaChamado(input);
		
		if (anexos != null 
				&& anexos.getAnexos() != null && !anexos.getAnexos().isEmpty()) {
			
			for(AnexoInput anexo : anexos.getAnexos()) {
				
				InputStream in = new ByteArrayInputStream(
						Base64.getDecoder().decode(anexo.getConteudoArquivo().getBytes()));
				
				String arquivo = storageService.armazenar(in, anexo.getNomeArquivo(), anexo.getTipoArquivo());
				
				ChamadoAnexo chamadoAnexo = new ChamadoAnexo();
				chamadoAnexo.setChamado(chamado);
				chamadoAnexo.setCaminho(arquivo);
				chamadoAnexo.setTipo(anexo.getTipoArquivo());
				chamadoAnexo.setNome(anexo.getNomeArquivo());
				
				
				if (chamado.getAnexos() == null) {
					chamado.setAnexos(new ArrayList<>());
				}
				
				chamado.getAnexos().add(chamadoAnexo);
				
			}
			
			chamado = chamadoRepository.save(chamado);
			
		}
		
		publisher.publishEvent(new NovoChamadoCriadoEvent(
				chamado.getId(), 
				chamado.getCliente().getRazao(), 
				chamado.getCliente().getFantasia(),
				false,
				chamado.getAssunto(),
				null
			)
		);
		
		return chamado;
	}
	
	
	@Transactional
	public Chamado criar(@Valid NovoChamadoInput input, List<MultipartFile> multiplosAnexos) {
		
		Chamado chamado = criaChamado(input);
		
		if (multiplosAnexos != null) {
			
			for (MultipartFile in : multiplosAnexos) {
				
				try {
					
					String arquivo = storageService.armazenar(in.getInputStream(), in.getOriginalFilename(), in.getContentType());
					
					ChamadoAnexo anexo = new ChamadoAnexo();
					anexo.setChamado(chamado);
					anexo.setCaminho(arquivo);
					anexo.setTipo(in.getContentType());
					
					if (chamado.getAnexos() == null) {
						chamado.setAnexos(new ArrayList<>());
					}
					
					chamado.getAnexos().add(anexo);
					
				} catch (Exception e) {
					log.error("Ocorreu um erro ao realizar a leitura do arquivo {}", in.getOriginalFilename());
				}
				
			}
			
			
			chamado = chamadoRepository.save(chamado);
		}
		
		publisher.publishEvent(new NovoChamadoCriadoEvent(
				chamado.getId(), 
				chamado.getCliente().getRazao(), 
				chamado.getCliente().getFantasia(),
				true,
				chamado.getAssunto(),
				chamado.getFoneAbertura()
			)
		);
		
		return chamado;
		
	}
	
	@Transactional
	public void adicionaAnexo(Long chamadoId, InputStream conteudo, String nome, String tipo) {
		
		Chamado chamado = buscarOuFalhar(chamadoId);
		
		String arquivo = storageService.armazenar(conteudo, nome, tipo);
		
		ChamadoAnexo anexo = new ChamadoAnexo();
		anexo.setChamado(chamado);
		anexo.setCaminho(arquivo);
		anexo.setNome(nome);
		anexo.setTipo(tipo);
		
		if(chamado.getAnexos() == null) {
			chamado.setAnexos(new ArrayList<>());
		}
		
		chamado.getAnexos().add(anexo);
		
		chamadoRepository.save(chamado);
		
	}
	
	@Transactional
	public void atribuirUsuario(Long chamadoId, Long usuarioId) {
		
		Chamado chamado = buscarOuFalhar(chamadoId);
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		Usuario usuarioAtribuidor = usuarioService.getUsuarioLogado();
		
		if (chamado.getUsuarioTratamento().getId().equals(usuario.getId())) {
			return;
		}
		
		chamado.setUsuarioTratamento(usuario);
		
		chamadoRepository.save(chamado);
		
		publisher.publishEvent(new AtribuirChamadoEvent(chamado, usuarioAtribuidor, usuario));
		
	}
	
	@Transactional
	public void encerrarChamado(Long chamadoId, @Valid EncerrarChamadoInput input) {
		
		Chamado chamado = buscarOuFalhar(chamadoId);
		
		chamado.setDescricaoFechamento(input.getDescricaoFechamento());
		chamado.setDataFechamento(OffsetDateTime.now());
		chamado.setStatusChamado(StatusChamado.FINALIZADO);
		
		chamadoRepository.save(chamado);
		
		publisher.publishEvent(new ChamadoEncerradoEvent(
				chamado.getId(), input.getDescricaoFechamento(), chamado.getUsuarioTratamento().getNome(), chamado.getFoneAbertura(), false));
		
	}
	
	@Transactional
	public void cancelarChamado(Long chamadoId, @Valid EncerrarChamadoInput input) {
		
		Chamado chamado = buscarOuFalhar(chamadoId);
		
		chamado.setDescricaoFechamento(input.getDescricaoFechamento());
		chamado.setDataFechamento(OffsetDateTime.now());
		chamado.setStatusChamado(StatusChamado.CANCELADO);
		
		chamadoRepository.save(chamado);
		
		publisher.publishEvent(new ChamadoEncerradoEvent(
				chamado.getId(), input.getDescricaoFechamento(), chamado.getUsuarioTratamento().getNome(), chamado.getFoneAbertura(), true));
		
	}
	
	@Transactional
	public void salvarChamado (Long chamadoId, SalvarChamadoInput input) {
		
		if (input.getPrioridade() < 1 || input.getPrioridade() > 3) {
			throw new NegocioException("A prioridade do chamado deve ser um número entre 1 (mais prioritário) e 3 (menos prioritário)");
		}
		
		Chamado chamado = buscarOuFalhar(chamadoId);
		
		chamado.setDescricaoFechamento(input.getDescricaoFechamento());
		chamado.setPrioridade(input.getPrioridade());
		
		chamadoRepository.save(chamado);
		
	}
	
	private Chamado criaChamado(ChamadoInput input) {
		
		Cliente cliente = clienteService.buscarPorIdOuFalhar(input.getClienteId());
		
		Chamado chamado = new Chamado();
		chamado.setCliente(cliente);
		chamado.setAssunto(input.getAssunto());
		chamado.setDataAbertura(OffsetDateTime.now());
		chamado.setDescricao(input.getDescricao());
		chamado.setPrioridade(2);
		chamado.setStatusChamado(StatusChamado.AGUARDANDO);
		chamado.setFoneAbertura(input.getFoneAbertura());
		
		chamado = chamadoRepository.save(chamado);
		
		return chamado;
		
	}
	
	private Chamado criaChamado(NovoChamadoInput input) {
		
		Cliente cliente = clienteService.buscarPorIdOuFalhar(input.getClienteId());
		
		Chamado chamado = new Chamado();
		chamado.setCliente(cliente);
		chamado.setAssunto(input.getAssunto());
		chamado.setDataAbertura(OffsetDateTime.now());
		chamado.setDescricao(input.getDescricao());
		chamado.setPrioridade(input.getPrioridade());
		chamado.setStatusChamado(StatusChamado.AGUARDANDO);
		chamado.setFoneAbertura("55" + input.getContato()
				.replace(" ", "")
				.replace("-", "")
				.replace("(", "")
				.replace(")", "")
				.replace("+", ""));
		
		chamado = chamadoRepository.save(chamado);
		
		return chamado;
		
	}
	
	

}
