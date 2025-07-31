package com.scm.validators;

import com.scm.dto.requests.StudentRegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StudentEmailValidator implements ConstraintValidator<ValidStudentEmail, StudentRegisterRequest> {

    @Override
    public boolean isValid(StudentRegisterRequest req, ConstraintValidatorContext context) {

        String expectedEmail = req.getMssv() + req.getFirstName() + "@ou.edu.vn";
        boolean match = expectedEmail.equalsIgnoreCase(req.getEmail());

        if (!match) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Email phải có định dạng: mssv + firstName + @ou.edu.vn")
                    .addPropertyNode("email").addConstraintViolation();
        }

        return match;
    }
}
