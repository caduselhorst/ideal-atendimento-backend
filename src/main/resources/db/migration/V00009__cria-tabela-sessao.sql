create table sessao (
	id bigint auto_increment primary key,
	passo integer not null,
	identificador varchar(30) not null,
	numero_formatado varchar(30),
	ultimo_texto_digitado longtext,
	tipo_ultimo_texto_digitado varchar(20),
	chamado_id bigint,
	data_inicio datetime,
	data_fim datetime
) engine=InnoDB default charset=utf8mb4;

create index idx_sessao_identificador on sessao (identificador);