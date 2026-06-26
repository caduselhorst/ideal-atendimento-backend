package br.com.developed.ideal.atendimento.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Validated
@Component
@ConfigurationProperties("aplicacao.wppconnectserver")
@Getter
@Setter
public class WppConnectServerProperties {
	
	@NotBlank
	private String baseUrl;
	
	@NotBlank
	private String token;

}
