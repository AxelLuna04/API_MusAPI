package com.musapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class CorreoService {

    private static final Logger log = LoggerFactory.getLogger(CorreoService.class);

    @Autowired
    private JavaMailSender mailSender;

    public boolean enviarCorreo(String destinatario, String asunto, String cuerpo) {
        try {
            if (destinatario == null || destinatario.trim().isEmpty()) {
                log.warn("Correo NO enviado: destinatario vacío.");
                return false;
            }

            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(destinatario);
            mensaje.setSubject(asunto);
            mensaje.setText(cuerpo);

            mailSender.send(mensaje);
            return true;

        } catch (MailException e) {
            log.error("Fallo enviando correo a {}: {}", destinatario, e.getMessage(), e);
            return false;
        } catch (Exception e) {
            log.error("Error inesperado enviando correo a {}: {}", destinatario, e.getMessage(), e);
            return false;
        }
    }
}

