package br.com.developed.ideal.atendimento.core.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("app.jwt")
@Getter
@Setter
public class JwtKeyStoreProperties {

	private String keystoreLocation;	
	private String keystorePassword;
	private String keyAlias;
	private String privateKeyPassphrase;	
	private Integer validityInSeconds = 30;
	
}
