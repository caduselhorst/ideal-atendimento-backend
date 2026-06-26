create table cliente (
	id bigint not null primary key,
	razao varchar(150),
	fantasia varchar(150),
	documento varchar(20),
	tipo integer,
	tipo_cadastro integer,
	endereco varchar(255),
	numero varchar(10),
	bairro varchar(150),
	cidade varchar(150),
	uf varchar(5),
	cep varchar(10),
	cod_ibge varchar(20),
	inativo boolean
) engine=InnoDB default charset=utf8mb4;

