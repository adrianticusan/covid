package com.covid19.match.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TooManyMessagesValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TooManyMessagesConstraint {
    String message() default "Too many messages sent!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
