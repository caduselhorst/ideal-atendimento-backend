insert into permissao (id, nome, descricao) values (15, 'MENSAGENS_PERMITE_CRIAR', 'Permite criar mensagens');
insert into permissao (id, nome, descricao) values (16, 'MENSAGENS_PERMITE_ALTERAR', 'Permite alterar mensagens');
insert into permissao (id, nome, descricao) values (17, 'MENSAGENS_PERMITE_EXCLUIR', 'Permite excluir mensagens');
insert into permissao (id, nome, descricao) values (18, 'MENSAGENS_PERMITE_LER', 'Permite consultar mensagens');

insert into perfil_permissao (perfil_id, permissao_id) values (1, 15);
insert into perfil_permissao (perfil_id, permissao_id) values (1, 16);
insert into perfil_permissao (perfil_id, permissao_id) values (1, 17);
insert into perfil_permissao (perfil_id, permissao_id) values (1, 18);
