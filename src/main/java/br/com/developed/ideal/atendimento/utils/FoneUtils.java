package br.com.developed.ideal.atendimento.utils;

public class FoneUtils {
	
	public static String normalizaFromHook(String fone) {
		
		String normalizado = fone.replace(" ", "")
				.replace("+", "")
				.replace("-", "");
		
		return normalizado;
		
	}

}
