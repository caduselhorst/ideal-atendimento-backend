alter table mensagem add usuario_inclusao_id bigint;
alter table mensagem add usuario_alteracao_id bigint;
alter table mensagem add usuario_exclusao_id bigint;

alter table mensagem add data_inclusao datetime;
alter table mensagem add data_alteracao datetime;
alter table mensagem add data_exclusao datetime;

alter table mensagem add constraint fk_mensagem_usucri foreign key (usuario_inclusao_id) references usuario (id);
alter table mensagem add constraint fk_mensagem_usualt foreign key (usuario_alteracao_id) references usuario (id);
alter table mensagem add constraint fk_mensagem_usuexc foreign key (usuario_exclusao_id) references usuario (id);