package com.scm.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StudentEmailValidator.class)
@Documented
public @interface ValidStudentEmail {

    String message() default "Email không đúng định dạng MSSV + FirstName + @ou.edu.vn";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
