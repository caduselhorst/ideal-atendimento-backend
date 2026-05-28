create table permissao (
	id bigint not null primary key,
	nome varchar(50) not null,
	descricao varchar(150)
) engine=InnoDB default charset=utf8mb4;


create table perfil (
	id bigint auto_increment primary key,
	nome varchar(60) not null,
	descricao varchar(255),
	padrao boolean not null
) engine=InnoDB default charset=utf8mb4;

create table usuario (
	id bigint not null auto_increment primary key,
	login varchar(100) not null,
	nome varchar(255) not null,
	senha varchar(255) not null,
	fone varchar(20),
	habilita_notificacao_whatsapp boolean not null,
	inativo boolean not null,
	email varchar(100)
) engine=InnoDB default charset=utf8mb4;

create table perfil_permissao (
	perfil_id bigint not null,
	permissao_id bigint not null,
	primary key (perfil_id, permissao_id),
	constraint fk_perfil_permissao_perfil foreign key (perfil_id) references perfil (id),
	constraint fk_perfil_permissao_perm foreign key (permissao_id) references permissao (id)
) engine=InnoDB default charset=utf8mb4;

create table usuario_perfil (
	usuario_id bigint not null,
	perfil_id bigint not null,
	primary key (usuario_id, perfil_id),
	constraint fk_usr_perfil_usuario foreign key (usuario_id) references usuario (id),
	constraint fk_usr_perfil_perfil foreign key (perfil_id) references perfil (id)
) engine=InnoDB default charset=utf8mb4;

create unique index idx_usuario_login on usuario (login);

insert into permissao values (1,  'USUARIOS_PERMITE_LER', 'Permite ler usuários');
insert into permissao values (2,  'USUARIOS_PERMITE_CRIAR', 'Permite criar usuários');
insert into permissao values (3,  'USUARIOS_PERMITE_ALTERAR', 'Permite alterar usuários');
insert into permissao values (4,  'USUARIOS_PERMITE_EXCLUIR', 'Permite excluir usuários');
insert into permissao values (5,  'PERFIS_PERMITE_LER', 'Permite ler perfis');
insert into permissao values (6,  'PERFIS_PERMITE_CRIAR', 'Permite criar perfis');
insert into permissao values (7,  'PERFIS_PERMITE_ALTERAR', 'Permite alterar perfis');
insert into permissao values (8,  'PERFIS_PERMITE_EXCLUIR', 'Permite excluir perfis');
insert into permissao values (9,  'PERMISSOES_PERMITE_LER', 'Permite ler permissões');
insert into permissao values (10, 'PERMISSOES_PERMITE_GERENCIAR', 'Permite gerenciar permissões');


insert into perfil (nome, descricao, padrao) values ('Administrador', 'Perfil administrador do sistema', false);

insert into perfil_permissao values (1, 1);
insert into perfil_permissao values (1, 2);
insert into perfil_permissao values (1, 3);
insert into perfil_permissao values (1, 4);
insert into perfil_permissao values (1, 5);
insert into perfil_permissao values (1, 6);
insert into perfil_permissao values (1, 7);
insert into perfil_permissao values (1, 8);
insert into perfil_permissao values (1, 9);
insert into perfil_permissao values (1, 10);



insert into usuario (login, nome, senha, fone, inativo, habilita_notificacao_whatsapp) values ('administrador', 'Usuario Administrador', '$2a$10$KT9XoJ/E80LwFr6W92It/OWqf5VFYGlg1G2azPyMjUsevvd5GiwB2', '(96) 98407-2732', false, false);

insert into usuario_perfil values (1, 1);