INSERT INTO idealatendimento.mensagem_bp
(chave, tipo, payload)
VALUES('sendMensagemInicial', 'TEXTO', '{
    "phone": "${phone}",
    "message": "Olá!!! Bem vindo à Ideal Informática!\\n\\nEsse canal é exclusivo para clientes Maxdata Sistemas.\\n\\nPor aqui você poderá solicitar seu suporte.\\n\\nPara começar, me informe o CNPJ da empresa.",
    "isGroup": false,
    "isNewsLetter": false,
    "isLid": false
}');
INSERT INTO idealatendimento.mensagem_bp
(chave, tipo, payload)
VALUES('sendMsgCnpjInvalido', 'TEXTO', '{
    "phone": "${phone}",
    "message": "Parece que o CNPJ informado é invalido.\\nTente novamente.",
    "isGroup": false,
    "isNewsLetter": false,
    "isLid": false
}');
INSERT INTO idealatendimento.mensagem_bp
(chave, tipo, payload)
VALUES('sendMsgClienteNaoEncontrado', 'TEXTO', '{
    "phone": "${phone}",
    "message": "Não consegui encontrar o cadastro pelo CNPJ informado.\\nEntre em contato com a Ideal Informática através de outro canal e tente novamente.",
    "isGroup": false,
    "isNewsLetter": false,
    "isLid": false
}');
INSERT INTO idealatendimento.mensagem_bp
(chave, tipo, payload)
VALUES('sendMsgFinalizacao', 'TEXTO', '{
    "phone": "${phone}",
    "message": "Agradecemos pelo contato\\n\\nIdeal Informática\\nA melhor loja de Automação Comercial do Estado do Amapá.",
    "isGroup": false,
    "isNewsLetter": false,
    "isLid": false
}');
INSERT INTO idealatendimento.mensagem_bp
(chave, tipo, payload)
VALUES('sendMsgIdentCliente', 'TEXTO', '{
    "phone": "${phone}",
    "message": "O atendimento será realizado para a empresa:\\n\\n*${razaoSocial}*",
    "isGroup": false,
    "isNewsLetter": false,
    "isLid": false
}');
INSERT INTO idealatendimento.mensagem_bp
(chave, tipo, payload)
VALUES('sendPerguntarNroSolicitação', 'TEXTO', '{
    "phone": "${phone}",
    "message": "Informe o número da solicitação",
    "isGroup": false,
    "isNewsLetter": false,
    "isLid": false
}');
INSERT INTO idealatendimento.mensagem_bp
(chave, tipo, payload)
VALUES('sendPerguntaAssunto', 'TEXTO', '{
    "phone": "${phone}",
    "message": "Informe o assunto da solicitação.\\nInforme um texto curto para identificar do que se trata. Exemplo:\\nErro ao concluir nota fiscal",
    "isGroup": false,
    "isNewsLetter": false,
    "isLid": false
}');
INSERT INTO idealatendimento.mensagem_bp
(chave, tipo, payload)
VALUES('sendPerguntaDetalhes', 'TEXTO', '{
    "phone": "${phone}",
    "message": "Agora informe detalhadamente o que acontece. Se preferir, pode enviar um áudio.\\nQuanto mais detalhado, melhor.",
    "isGroup": false,
    "isNewsLetter": false,
    "isLid": false
}');
INSERT INTO idealatendimento.mensagem_bp
(chave, tipo, payload)
VALUES('sendMenu', 'LISTA', '{
  "phone": "${phone}",
  "isGroup": false,
  "description": "Seleciona uma das opções\\n\\n1 - Criar solicitação\\n2 - Consultar status\\n3 - Sair\\n\\n👇",
  "buttonText": "Opções",
  "sections": [
    {
      "title": "Opções",
      "rows": [
        {
          "rowId": "1",
          "title": "Criar solicitação"
        },
        {
          "rowId": "2",
          "title": "Consultar status"
        },
        {
          "rowId": "3",
          "title": "Sair"
        }
      ]
    }
  ]
}');
INSERT INTO idealatendimento.mensagem_bp
(chave, tipo, payload)
VALUES('sendOpcaoInvalida', 'TEXTO', '{
    "phone": "${phone}",
    "message": "Opção inválida.\\nUtilize uma das opções.",
    "isGroup": false,
    "isNewsLetter": false,
    "isLid": false
}');
INSERT INTO idealatendimento.mensagem_bp
(chave, tipo, payload)
VALUES('sendPerguntaAnexos', 'LISTA', '{
  "phone": "${phone}",
  "isGroup": false,
  "description": "Deseja informar anexos?\\n\\n1 - Sim\\n2 - Não\\n\\n👇",
  "buttonText": "Opções",
  "sections": [
    {
      "title": "Opções",
      "rows": [
        {
          "rowId": "1",
          "title": "Sim"
        },
        {
          "rowId": "2",
          "title": "Não"
        }
      ]
    }
  ]
}');
INSERT INTO idealatendimento.mensagem_bp
(chave, tipo, payload)
VALUES('sendSolicitacaoAnexo', 'TEXTO', '{
    "phone": "${phone}",
    "message": "Envie o arquivo.\\nPode ser uma imagem, um documento, um áudio, um video.",
    "isGroup": false,
    "isNewsLetter": false,
    "isLid": false
}');
INSERT INTO idealatendimento.mensagem_bp
(chave, tipo, payload)
VALUES('sendMsgChamadoCriado', 'TEXTO', '{
    "phone": "${phone}",
    "message": "OK! Solicitação nro. *${nroChamado}* criada com sucesso e está *Aguardando Atendimento*.\\nVocê receberá notificações do andamento por aqui.\\n\\nSe desejar, poderá consultar essa solicitações no menu principal.",
    "isGroup": false,
    "isNewsLetter": false,
    "isLid": false
}');
INSERT INTO idealatendimento.mensagem_bp
(chave, tipo, payload)
VALUES('sendMaisAnexo', 'LISTA', '{
  "phone": "${phone}",
  "isGroup": false,
  "description": "Deseja informar mais um?\\n\\n1 - Sim\\n2 - Não\\n\\n👇",
  "buttonText": "Opções",
  "sections": [
    {
      "title": "Opções",
      "rows": [
        {
          "rowId": "1",
          "title": "Sim"
        },
        {
          "rowId": "2",
          "title": "Não"
        }
      ]
    }
  ]
}');
INSERT INTO idealatendimento.mensagem_bp
(chave, tipo, payload)
VALUES('sendNotificacaoChamadoEmAtendimento', 'TEXTO', '{
    "phone": "${phone}",
    "message": "📢 Ideal informatica informa!!\\n\\nO chamado nº *${nroChamado}* está em tratamento pelo analista *${usuarioTratamento}*",
    "isGroup": false,
    "isNewsLetter": false,
    "isLid": false
}');
INSERT INTO idealatendimento.mensagem_bp
(chave, tipo, payload)
VALUES('sendNotificacaoNovoChamado', 'TEXTO', '{
    "phone": "${phone}",
    "message": "📢 Ideal informatica informa!!\\n\\nNova solicitação de atendimento:\\n\\nNro: *${chamadoId}*\\nCliente: *${razao} (${fantasia})*\\n\\n",
    "isGroup": false,
    "isNewsLetter": false,
    "isLid": false
}');
INSERT INTO idealatendimento.mensagem_bp
(chave, tipo, payload)
VALUES('sendNotificacaoChamadoEncerrado', 'TEXTO', '{
    "phone": "${phone}",
    "message": "📢 Ideal informatica informa!!\\n\\nO chamado nº *${nroChamado}* foi *encerrado* pelo analista *${usuario}*, com a seguinte descrição:\\n\\n${descricaoFechamento}",
    "isGroup": false,
    "isNewsLetter": false,
    "isLid": false
}');
INSERT INTO idealatendimento.mensagem_bp
(chave, tipo, payload)
VALUES('sendNotificacaoChamadoCancelado', 'TEXTO', '{
    "phone": "${phone}",
    "message": "📢 Ideal informatica informa!!\\n\\nO chamado nº *${nroChamado}* foi *cancelado* pelo analista *${usuario}*, com a seguinte descrição:\\n\\n${descricaoFechamento}",
    "isGroup": false,
    "isNewsLetter": false,
    "isLid": false
}');
INSERT INTO idealatendimento.mensagem_bp
(chave, tipo, payload)
VALUES('sendNotificacaoNovoChamadoWebCliente', 'TEXTO', '{
    "phone": "${phone}",
    "message": "📢 Ideal informatica informa!!\\n\\nCaro cliente, informamos que foi criada a solitação de atendimento nº *${chamadoId}*, com o assunto *${assunto}*.\\n\\nNovas notificações sobre essa solicitação serão enviadas por aqui.",
    "isGroup": false,
    "isNewsLetter": false,
    "isLid": false
}');
INSERT INTO idealatendimento.mensagem_bp
(chave, tipo, payload)
VALUES('sendMsgChamadoNaoEncontrado', 'TEXTO', '{
    "phone": "${phone}",
    "message": "Solicitação não encontrada.\\nA solicitação foi para esse CNPJ?",
    "isGroup": false,
    "isNewsLetter": false,
    "isLid": false
}');
INSERT INTO idealatendimento.mensagem_bp
(chave, tipo, payload)
VALUES('sendMsgChamadoEncontrado', 'TEXTO', '{
    "phone": "${phone}",
    "message": "Status da solicitação:\\n\\n*Nro solicitação*: ${chamadoId}\\n*Data*: ${dataAbertura}\\n*Status*: ${status}\\n*Analista*: ${analista}\\n*Data encerramento*: ${dataEncerramento}\\n*Descricao encerramento*: ${descricaoEncerramento}\\n\\n",
    "isGroup": false,
    "isNewsLetter": false,
    "isLid": false
}');
INSERT INTO idealatendimento.mensagem_bp
(chave, tipo, payload)
VALUES('sendNotificacaoChamadoAtribuido', 'TEXTO', '{
    "phone": "${phone}",
    "message": "📢 Ideal informatica informa!!\\n\\nO chamado nº *${chamadoId}* foi atribuido a você por ${usuarioAtribuidor}",
    "isGroup": false,
    "isNewsLetter": false,
    "isLid": false
}');