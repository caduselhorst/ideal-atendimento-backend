package br.com.developed.ideal.atendimento.domain.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.developed.ideal.atendimento.domain.exception.StorageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageService {
	
	private final Path repositorio;
	
	
	public String armazenar(Path diretorio, InputStream inputStream, String nomeOriginal, String contentType) {
		
		try {
			
			 Files.createDirectories(diretorio);

	            String nomeArquivo = UUID.randomUUID()
	                    .toString()
	                    .replace("-", "")
	                    + getExtensao(nomeOriginal);

	            Path caminhoCompleto = diretorio.resolve(nomeArquivo);

	            Files.copy(
	                    inputStream,
	                    caminhoCompleto,
	                    StandardCopyOption.REPLACE_EXISTING);

	            return nomeArquivo;

			
		} catch (IOException e) {
			
			log.error("Ocorreu um erro ao criar o arquivo", e);
			
			throw new StorageException("Ocorreu um erro ao criar o arquivo", e);
			
		}
		
	}
	
	public String armazenar(InputStream inputStream, String nomeOriginal, String contentType) {
		
		return armazenar(repositorio, inputStream, nomeOriginal, contentType);
		
	}
	
	private String getExtensao(String nomeArquivo) {
		if (nomeArquivo == null || nomeArquivo.isBlank()) {
			return "";
		}

		int posicao = nomeArquivo.lastIndexOf('.');

		if (posicao < 0) {
			return "";
		}

		return nomeArquivo.substring(posicao);
	}
	
	public Path recuperar(String nomeArquivo) {
	    return repositorio.resolve(nomeArquivo);
	}

	public void remover(String nomeArquivo) {
	    try {
	        Files.deleteIfExists(repositorio.resolve(nomeArquivo));
	    } catch (IOException e) {
	        throw new StorageException(
	                "Erro ao remover arquivo",
	                e);
	    }
	}


}
