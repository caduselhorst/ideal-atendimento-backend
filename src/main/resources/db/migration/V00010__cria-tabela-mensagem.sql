create table mensagem (
	id bigint auto_increment primary key,
	passo integer not null,
	mensagem longtext
) engine=InnoDB default charset=utf8mb4;

create unique index idx_mensagem on mensagem (passo);