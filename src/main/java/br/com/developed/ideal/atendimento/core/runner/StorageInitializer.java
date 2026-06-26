package br.com.developed.ideal.atendimento.core.runner;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.com.developed.ideal.atendimento.core.properties.StorageProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class StorageInitializer implements ApplicationRunner {
	
	private final StorageProperties storageProperties;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		Path storageRoot = Path.of(storageProperties.getRoot());

        Files.createDirectories(storageRoot);

        Path anexosPath =
                storageRoot.resolve(storageProperties.getAnexosDir());

        Files.createDirectories(anexosPath);

        log.info("Storage root: {}", storageRoot);
        log.info("Diretório de anexos: {}", anexosPath);
		
		
	}
	
	

}
