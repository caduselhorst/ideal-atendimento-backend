package br.com.developed.ideal.atendimento.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;

public class AppUtils {
	
	public static String toMD5(String input) {
		
		 try {
	            // Cria uma instância do algoritmo MD5
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            // Calcula o hash em bytes
	            byte[] messageDigest = md.digest(input.getBytes());
	            
	            // Converte os bytes em um valor hexadecimal
	            StringBuilder hexString = new StringBuilder();
	            for (byte b : messageDigest) {
	                String hex = Integer.toHexString(0xff & b);
	                if (hex.length() == 1) {
	                    hexString.append('0');
	                }
	                hexString.append(hex);
	            }
	            return hexString.toString();
	        } catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException("Erro ao calcular MD5: " + e.getMessage(), e);
	        }
		
	}
	
	public static String generateRandomSequence() {
		
        Random random = new Random();
        int randomNumber = 1000 + random.nextInt(9000); // Gera um número entre 1000 e 9999
        return String.valueOf(randomNumber);
        
    }
	
	
	// Método para gerar uma String aleatória
    public static String generateRandomString(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("O comprimento deve ser maior que 0.");
        }

        // Conjunto de caracteres permitidos na String
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder randomString = new StringBuilder(length);

        // Gerar caracteres aleatórios e adicionar à StringBuilder
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(chars.length());
            randomString.append(chars.charAt(randomIndex));
        }

        return randomString.toString();
    }
    
    public static String getExtensaoArquivoPeloMimeType(String mimeType) {
    	
    	try {
    		
    		if (mimeType.startsWith("audio/ogg")) {
    			return ".ogg";
    		}
    		
	    	MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
	    	MimeType mime = allTypes.forName(mimeType);
	    	
	    	return mime.getExtension();
	    	
    	} catch (MimeTypeException e) {
    		throw new RuntimeException("Não foi possível definir a extensão do arquivo para o tipo " + mimeType);
    	}
    	
    }

}
