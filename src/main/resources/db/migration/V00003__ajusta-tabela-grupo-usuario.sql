alter table grupo_usuario add usuario_responsavel_id bigint;

alter table grupo_usuario add constraint fk_grupousu_usuresp foreign key (usuario_responsavel_id) references usuario (id);