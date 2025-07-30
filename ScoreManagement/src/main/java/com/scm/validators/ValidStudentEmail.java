package com.scm.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StudentEmailValidator.class)
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidStudentEmail {

    String message() default "Email không đúng định dạng MSSV + FirstName + @ou.edu.vn";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
