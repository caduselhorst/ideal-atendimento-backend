create table grupo_usuario (
	id bigint auto_increment not null primary key,
	descricao varchar(100) not null,
	identificadorbpms varchar(50) not null,
	data_inclusao datetime,
	data_alteracao datetime,
	data_exclusao datetime,
	usuario_inclusao_id bigint,
	usuario_alteracao_id bigint,
	usuario_exclusao_id bigint,
	constraint fk_grpusucri_usu foreign key (usuario_inclusao_id) references usuario (id),
	constraint fk_grpusualt_usu foreign key (usuario_alteracao_id) references usuario (id),
	constraint fk_grpusuexc_usu foreign key (usuario_exclusao_id) references usuario (id)
) engine=InnoDB default charset=utf8mb4;


create table usuario_grupo (
	usuario_id bigint,
	grupo_usuario_id bigint,
	constraint pk_usuariogrupo primary key (usuario_id, grupo_usuario_id),
	constraint fk_usugrp_grp foreign key (grupo_usuario_id) references grupo_usuario (id),
	constraint fk_usugrp_usu foreign key (usuario_id) references usuario (id)
) engine=InnoDB default charset=utf8mb4;

create unique index idx_grupo_usario_bp on grupo_usuario (identificadorbpms);

insert into permissao (id, nome, descricao) values (11, 'GRUPOS_USUARIO_PERMITE_CRIAR', 'Permite criar grupos de usuário');
insert into permissao (id, nome, descricao) values (12, 'GRUPOS_USUARIO_PERMITE_ALTERAR', 'Permite alterar grupos de usuário');
insert into permissao (id, nome, descricao) values (13, 'GRUPOS_USUARIO_PERMITE_EXCLUIR', 'Permite excluir grupos de usuário');
insert into permissao (id, nome, descricao) values (14, 'GRUPOS_USUARIO_PERMITE_LER', 'Permite consultar grupos de usuários');

insert into perfil_permissao (perfil_id, permissao_id) values (1, 11);
insert into perfil_permissao (perfil_id, permissao_id) values (1, 12);
insert into perfil_permissao (perfil_id, permissao_id) values (1, 13);
insert into perfil_permissao (perfil_id, permissao_id) values (1, 14);