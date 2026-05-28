package br.com.developed.ideal.atendimento.core.validation;

import java.util.Arrays;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;



public class EstadoValidator implements ConstraintValidator<Estado, String> {

	private static final String[] ESTADOS = {"ac", "al", "ap", "am", "ba", "ce", "df", "es", "go", "ma", "mt",
	                                         "ms", "mg", "pa", "pb", "pr", "pe", "pi", "rj", "rn", "rs", "ro", 
	                                         "rr", "sc", "sp", "se", "to"};	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return Arrays.asList(ESTADOS).contains(value.toLowerCase());
	}

}
