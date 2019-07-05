package pl.edu.kopalniakodu.pickide.domain.validator;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class NonWhitespaceValidator implements ConstraintValidator<NonWhitespace, String> {
    @Override
    public void initialize(NonWhitespace constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean result = value.contains(" ");
        return !result;
    }

}



