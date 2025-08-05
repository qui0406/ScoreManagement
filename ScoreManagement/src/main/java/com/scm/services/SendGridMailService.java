package com.scm.services;


import com.scm.dto.requests.EmailRequest;
import com.scm.dto.responses.EmailResponse;

public interface SendGridMailService {
    EmailResponse sendMail(EmailRequest emailRequest);
}
