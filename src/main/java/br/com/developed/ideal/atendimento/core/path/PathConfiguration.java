package br.com.developed.ideal.atendimento.core.path;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.developed.ideal.atendimento.core.properties.StorageProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class PathConfiguration {
	
	private final StorageProperties storageProperties;
	
	@Bean
	Path getPath() {
		
		Path path = Path.of(
				storageProperties.getRoot(),
				storageProperties.getAnexosDir());

		try {
        Files.createDirectories(path);

        return path;
		} catch (IOException e) {
			log.error("Não foi possível armazenar o diretório de armazenamento", e);
			throw new IllegalStateException("Não foi possivel inicializar o diretório de armazenamento", e);
			
		}
		
	}

}
