package br.com.developed.ideal.atendimento.api.model;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MensagemModel extends MensagemResumoModel {
	
	private OffsetDateTime dataInclusao;
	private OffsetDateTime dataAlteracao;
	private OffsetDateTime dataExclusao;
	
	private UsuarioResumoModel usuarioInclusao;
	private UsuarioResumoModel usuarioAlteracao;
	private UsuarioResumoModel usuarioExclusao;

}
