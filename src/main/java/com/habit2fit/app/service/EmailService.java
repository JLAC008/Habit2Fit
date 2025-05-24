package com.habit2fit.app.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
@Service
public class EmailService {
	
	@Value("${resend.api.key}")
    private String resendApiKey;
	
	private static final Logger logger = LogManager.getLogger(EmailService.class);

    public void sendEmail() {
        Resend resend = new Resend(resendApiKey);

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("Acme <onboarding@resend.dev>")
                .to("habit2fit@gmail.com")
                .subject("it works!")
                .html("<strong>hello world</strong>")
                .build();

        try {
            CreateEmailResponse data = resend.emails().send(params);
            logger.info("ID del correo enviado: " + data.getId());
        } catch (ResendException e) {
        	logger.error("Error al enviar el correo" +e);
        }
    }
}
