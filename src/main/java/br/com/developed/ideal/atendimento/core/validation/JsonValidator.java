package br.com.developed.ideal.atendimento.core.validation;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class JsonValidator implements ConstraintValidator<Json, String> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true; // Use @NotNull ou @NotBlank para validar campos vazios
        }
        try {
            mapper.readTree(value);
            return true; // O JSON está bem formado
        } catch (Exception e) {
            return false; // Falha ao fazer o parse, a string é um JSON inválido
        }
    }
}
