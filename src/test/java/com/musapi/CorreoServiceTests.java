package com.musapi;

import com.musapi.service.CorreoService;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

public class CorreoServiceTests {

    @InjectMocks
    private CorreoService correoService;

    @Mock
    private JavaMailSender mailSender;

    @Captor
    private ArgumentCaptor<SimpleMailMessage> mensajeCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void enviarCorreo_deberiaConstruirYEnviarMensajeCorrectamente() {
        String destinatario = "usuario@musapi.com";
        String asunto = "Bienvenido";
        String cuerpo = "Gracias por registrarte en Musapi";

        correoService.enviarCorreo(destinatario, asunto, cuerpo);

        verify(mailSender).send(mensajeCaptor.capture());

        SimpleMailMessage mensajeEnviado = mensajeCaptor.getValue();
        assertArrayEquals(new String[]{destinatario}, mensajeEnviado.getTo());
        assertEquals(asunto, mensajeEnviado.getSubject());
        assertEquals(cuerpo, mensajeEnviado.getText());
    }
}
