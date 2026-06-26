create table chamado (
	id bigint auto_increment primary key,
	cliente_id bigint not null,
	data_abertura datetime not null,
	previsao_fechamento datetime,
	data_fechamento datetime,
	usuario_tratamento_id bigint,
	prioridade integer,
	descricao longtext,
	descricao_fechamento longtext,
	status_chamado varchar(20),
	constraint fk_chamado_cliente foreign key (cliente_id) references cliente (id),
	constraint fl_chamado_usuario foreign key (usuario_tratamento_id) references usuario (id)
) engine=InnoDB default charset=utf8mb4;

create table chamado_anexo (
	id bigint auto_increment primary key,
	chamado_id bigint,
	nome varchar(255),
	tipo varchar(100),
	conteudo longtext,
	constraint fk_chamadoanexo_chamado foreign key (chamado_id) references chamado (id)
) engine=InnoDB default charset=utf8mb4;

create table chamado_log (
	id bigint auto_increment primary key,
	chamado_id bigint not null ,
	data datetime not null,
	acao varchar(1000),
	constraint fk_chamadolog_chamado foreign key (chamado_id) references chamado (id)
) engine=InnoDB default charset=utf8mb4;