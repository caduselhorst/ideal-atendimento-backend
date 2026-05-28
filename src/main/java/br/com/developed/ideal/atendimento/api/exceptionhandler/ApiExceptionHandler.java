package br.com.developed.ideal.atendimento.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import br.com.developed.ideal.atendimento.core.validation.ValidacaoException;
import br.com.developed.ideal.atendimento.domain.exception.CepMalFormadoException;
import br.com.developed.ideal.atendimento.domain.exception.EntidadeEmUsoException;
import br.com.developed.ideal.atendimento.domain.exception.EntidadeNaoEncontradaException;
import br.com.developed.ideal.atendimento.domain.exception.NegocioException;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final String MENSAGEM_SEM_PERMISSAO = "Você não possui permissão para executar essa operação";
	private static final String MENSAGEM_ERRO_GENERICO_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. "
			+ "Tente novamente. Se o problema persistir entre em contato com o Administrador";
	private static final String MENSAGEM_USUARIO_OU_SENHA_INVALIDOS = "Usuário e/ou senha inválidos";

	@Autowired
	private MessageSource messageSource;
	
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		return ResponseEntity.status(status).headers(headers).build();
	}

//	@Override
//	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatusCode status,
//			WebRequest request) {
//		
//		return handleValidationArgumentNotValid(ex, ex.getBindingResult(),headers, status, request);
//		
//	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		/*Pega causa raiz*/
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		// tratamento de erro de corpo da requisição
		if(rootCause instanceof InvalidFormatException) {
			return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
		}
		
		// Tratamento de erro para propriedades inválidas ou ignoradas
		if(rootCause instanceof PropertyBindingException) {
			return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
		}
		

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da requisição está inválido. Verifique o erro de sintaxe";
		
		Problem problem = createProblemBuilder((HttpStatus) status, problemType, detail)
				.userMessage(MENSAGEM_ERRO_GENERICO_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, 
				new HttpHeaders(), status, request);
	}

	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		if (ex instanceof MethodArgumentTypeMismatchException) {
	        return handleMethodArgumentTypeMismatch(
	                (MethodArgumentTypeMismatchException) ex, headers, status, request);
	    }

	    return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		
		String detail = String.format("O recurso %s, que você tentou acessar é inexistente", 
				ex.getRequestURL());
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		
		Problem problem = createProblemBuilder((HttpStatus) status, problemType, detail)
				.userMessage(MENSAGEM_ERRO_GENERICO_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}


	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		return handleValidationArgumentNotValid(ex, ex.getBindingResult(),headers, status, request);
				
	}


	private ResponseEntity<Object> handleValidationArgumentNotValid(Exception ex, BindingResult bindingResult,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		String detail = "Um ou mais campos estão inválidos. Faça o preencimento correto e tente novamente";
		
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		//ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		
		List<Problem.Object> problemObjects = bindingResult.getAllErrors()
				.stream()
				.map(objectError -> {
					
					String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
					String name = objectError.getObjectName();
					
					if(objectError instanceof FieldError) {
						name = ((FieldError) objectError).getField();
					}
					
					return Problem.Object.builder()
						.name(name)
						.userMessage(message)
						.build();
				})
				.collect(Collectors.toList());
		
		Problem problem = createProblemBuilder((HttpStatus)status, problemType, detail)
				.userMessage(detail)
				.objects(problemObjects)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		
		if(body == null) {
			body = Problem.builder()
					.title(((HttpStatus) status).getReasonPhrase())
					.status(status.value())
					.timestamp(OffsetDateTime.now())
					.userMessage(MENSAGEM_ERRO_GENERICO_USUARIO_FINAL)
					.build();
		} else if (body instanceof String) {
			body = Problem.builder() 
					.title((String) body)
					.status(status.value())
					.timestamp(OffsetDateTime.now())
					.userMessage(MENSAGEM_ERRO_GENERICO_USUARIO_FINAL)
					.build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		String detail = "Os parametros [%s] são obrigatórios. Verifique e tente novamente";
		
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		
		Object[] parametros = ex.getDetailMessageArguments();
		
		String sParametros = Arrays.asList(parametros).stream().map(e -> (String) e).collect(Collectors.joining(","));
		
		detail = String.format(detail, sParametros);
		
		Problem problem = createProblemBuilder((HttpStatus)status, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e, 
			WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		
		System.out.println(e.getConstraintViolations());
		
		String detail = "Informe o timeOffset no formato +00:00";
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(e, problem, 
				new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e, 
			WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = e.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(e, problem, 
				new HttpHeaders(), status, request);
		
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, 
			WebRequest request) {
		
		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		String detail = e.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(e, problem, 
				new HttpHeaders(), status, request);
		
	}
	
	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<?> handleValidacaoException(ValidacaoException e, WebRequest request) {
		
		HttpHeaders headers = new HttpHeaders();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		return handleValidationArgumentNotValid(e, e.getBindingResult(), headers, status, request);
		
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException e, 
			WebRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		String detail = e.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(e, problem, 
				new HttpHeaders(), status, request);
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleUncaught(Exception ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
		
		String detail = MENSAGEM_ERRO_GENERICO_USUARIO_FINAL;
		
		ex.printStackTrace();
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MENSAGEM_ERRO_GENERICO_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
				
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleUncaught(AccessDeniedException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.FORBIDDEN;
		ProblemType problemType = ProblemType.ACESSO_NEGADO;
		
		String detail = ex.getMessage();
		
		ex.printStackTrace();
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MENSAGEM_SEM_PERMISSAO)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
				
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		ProblemType problemType = ProblemType.ACESSO_NEGADO;
		
		String detail = ex.getMessage();
		
		ex.printStackTrace();
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MENSAGEM_USUARIO_OU_SENHA_INVALIDOS)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
				
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handleUsernameNotFoundException(BadCredentialsException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		ProblemType problemType = ProblemType.ACESSO_NEGADO;
		
		String detail = ex.getMessage();
		
		ex.printStackTrace();
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(ex.getMessage())
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
				
	}
	
	@ExceptionHandler(CepMalFormadoException.class)
	public ResponseEntity<?> handleUsernameNotFoundException(CepMalFormadoException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		
		String detail = ex.getMessage();
		
		ex.printStackTrace();
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(ex.getMessage())
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
				
	}
	
	@ExceptionHandler(PropertyReferenceException.class)
	public ResponseEntity<?> handlePropertyReferenceException(PropertyReferenceException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		
		
		
		String detail = String.format("Não foi encontrada a propriedade %s informada na requisição", ex.getPropertyName());
		
		ex.printStackTrace();
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
				
	}
	
		
	/*
	 * Especializações
	 */
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
	        MethodArgumentTypeMismatchException ex, HttpHeaders headers,
	        HttpStatusCode status, WebRequest request) {

	    ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;

	    String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
	            + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
	            ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

	    Problem problem = createProblemBuilder((HttpStatus) status, problemType, detail)
	    		.userMessage(MENSAGEM_ERRO_GENERICO_USUARIO_FINAL)
	    		.build();

	    return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException e,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		String path = joinPath(e);
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		
		String detail = String.format("A propriedade '%s' "
					+ "não é uma propriedade conhecida de '%s'. Verifique e tente novamente.", 
					path, e.getReferringClass().getSimpleName());		
		
		Problem problem =  createProblemBuilder((HttpStatus) status, problemType, detail)
				.userMessage(MENSAGEM_ERRO_GENERICO_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(e, problem, headers, status, request);
		
	}


	private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		
		String path = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
				+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s", 
				path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		Problem problem =  createProblemBuilder((HttpStatus) status, problemType, detail)
				.userMessage(MENSAGEM_ERRO_GENERICO_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
		return Problem.builder()
				.detail(detail)
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.timestamp(OffsetDateTime.now());

	}
	
	private String joinPath(PropertyBindingException e) {
		return e.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
	}

}
