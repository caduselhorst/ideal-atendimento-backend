alter table usuario add documento varchar(20);

create unique index idx_usuario_documento on usuario (documento);