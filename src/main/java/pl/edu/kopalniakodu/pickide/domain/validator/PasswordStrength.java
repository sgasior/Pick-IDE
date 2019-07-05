package pl.edu.kopalniakodu.pickide.domain.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

@Documented
@Constraint(validatedBy = PasswordStrengthValidator.class)
@Target({METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordStrength {
    String message() default "Password must contain minimum 6 characters, " +
            "1 uppercase and 1 lowercasec character, at least 1 one digit,  1 special character";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
