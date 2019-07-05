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
@Constraint(validatedBy = NonWhitespaceValidator.class)
@Target({METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NonWhitespace {
    String message() default "Whitespaces not allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
