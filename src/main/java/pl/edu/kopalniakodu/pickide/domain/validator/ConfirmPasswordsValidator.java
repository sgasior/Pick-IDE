package pl.edu.kopalniakodu.pickide.domain.validator;

import pl.edu.kopalniakodu.pickide.domain.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConfirmPasswordsValidator implements ConstraintValidator<ConfirmPasswords, User> {


    @Override
    public void initialize(ConfirmPasswords constraintAnnotation) {

    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        return user.getPassword().equals(user.getConfirmPassword());
    }
}


