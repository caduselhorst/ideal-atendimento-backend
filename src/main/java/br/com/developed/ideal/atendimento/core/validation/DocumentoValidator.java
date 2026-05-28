package br.com.developed.ideal.atendimento.core.validation;

import org.apache.commons.lang3.StringUtils;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DocumentoValidator implements ConstraintValidator<Documento, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if(value == null) {
			return false;
		}
		
		String valorSemFormato = value
				.replace(".", "")
				.replace("-", "")
				.replace("/", "")
				.replace(" ", "");
		
		if(valorSemFormato.length() <= 11) {
			// cpf
			
			valorSemFormato = StringUtils.leftPad(valorSemFormato, 11, '0');
			
			CPFValidator validator = new CPFValidator();
			
			if(!validator.invalidMessagesFor(valorSemFormato).isEmpty()) {
				return false;
			} else {
				return true;
			}
		} else {
			
			if(valorSemFormato.length() <= 14) {
				
				valorSemFormato = StringUtils.leftPad(valorSemFormato, 14, '0');
				
				CNPJValidator validator = new CNPJValidator();
				
				if(!validator.invalidMessagesFor(valorSemFormato).isEmpty()) {
					return false;
				} else {
					return true;
				}
			} else {
				return false;
			}
			
		}

	}
	

}
