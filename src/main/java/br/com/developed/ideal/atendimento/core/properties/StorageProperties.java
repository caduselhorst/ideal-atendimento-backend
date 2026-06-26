package br.com.developed.ideal.atendimento.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Validated
@Component
@ConfigurationProperties("aplicacao.storage")
@Getter
@Setter
public class StorageProperties {
	
	@NotBlank
	private String root;
	
	@NotBlank
	private String anexosDir; 

}
