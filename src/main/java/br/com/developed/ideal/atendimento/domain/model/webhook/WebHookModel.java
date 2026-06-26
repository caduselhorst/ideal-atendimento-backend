package br.com.developed.ideal.atendimento.domain.model.webhook;

public record WebHookModel(
		String evento,
		String body,
		String type,
		String from,
		String contentType,
		String fileName,
		String formattedNumber,
		String listResponse
) {}
