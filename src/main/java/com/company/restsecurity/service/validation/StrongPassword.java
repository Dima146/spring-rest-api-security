package com.company.restsecurity.service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StrongPassword {

    String message() default "must be at least 8 characters in length, a minimum of 1 lowercase letter," +
            "1 uppercase letter, 1 numeric character, 1 special character " +
            "and not contain the alphabetical sequence of 5 letters";


    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
