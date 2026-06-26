create table mensagem_bp (
	
	id bigint auto_increment primary key,
	chave varchar(50) not null,
	tipo varchar(50) not null,
	payload longtext
	
) engine=InnoDB default charset=utf8mb4;

create unique index mensagembp_chave on mensagem_bp (chave);