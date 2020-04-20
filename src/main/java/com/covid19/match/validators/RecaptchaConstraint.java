package com.covid19.match.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RecaptchaValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RecaptchaConstraint {
    String message() default "Invalidat captcha";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
