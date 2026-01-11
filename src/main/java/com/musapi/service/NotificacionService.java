package com.musapi.service;

import com.musapi.dto.NotificacionDTO;
import com.musapi.model.ContenidoGuardado;
import com.musapi.model.Notificacion;
import com.musapi.model.PerfilArtista;
import com.musapi.model.Usuario;
import com.musapi.repository.ContenidoGuardadoRepository;
import com.musapi.repository.NotificacionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    private static final Logger log = LoggerFactory.getLogger(NotificacionService.class);

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private ContenidoGuardadoRepository contenidoGuardadoRepository;

    @Autowired
    private CorreoService correoService;

    public List<NotificacionDTO> obtenerNotificacionesPendientes(Integer idUsuario) {
        List<Notificacion> notificaciones = notificacionRepository
                .findByUsuario_IdUsuarioAndFueLeidaFalse(idUsuario);

        return notificaciones.stream()
                .map(n -> new NotificacionDTO(n.getIdNotificacion(), n.getMensaje(), n.getFechaEnvio().toString()))
                .collect(Collectors.toList());
    }

    public void notificarSeguidores(PerfilArtista perfilArtista, String mensajeNotificacion) {
        if (perfilArtista == null) return;

        List<ContenidoGuardado> seguidores = contenidoGuardadoRepository.findByPerfilArtista(perfilArtista);
        if (seguidores == null || seguidores.isEmpty()) return;

        String asunto = "Nuevo contenido de " + perfilArtista.getUsuario().getNombreUsuario();

        for (ContenidoGuardado contenido : seguidores) {
            try {
                Usuario usuarioSeguidor = contenido.getUsuario();
                if (usuarioSeguidor == null) continue;

                Notificacion notificacion = new Notificacion();
                notificacion.setUsuario(usuarioSeguidor);
                notificacion.setMensaje(mensajeNotificacion);
                notificacion.setFechaEnvio(LocalDate.now());
                notificacion.setFueLeida(false);
                notificacionRepository.save(notificacion);

                boolean enviado = correoService.enviarCorreo(usuarioSeguidor.getCorreo(), asunto, mensajeNotificacion);
                if (!enviado) {
                    log.warn("No se pudo enviar correo a {} pero la notificación en BD sí se guardó.",
                            usuarioSeguidor.getCorreo());
                }

            } catch (Exception e) {
                log.error("Error notificando a un seguidor: {}", e.getMessage(), e);
            }
        }
    }

    public boolean marcarNotificacionComoLeida(Integer idNotificacion) {
        Optional<Notificacion> optNoti = notificacionRepository.findById(idNotificacion);

        if (optNoti.isPresent()) {
            Notificacion noti = optNoti.get();
            noti.setFueLeida(true);
            notificacionRepository.save(noti);
            return true;
        }
        return false;
    }

    public boolean marcarTodasComoLeidas(Integer idUsuario) {
        List<Notificacion> notificaciones = notificacionRepository.findByUsuario_IdUsuarioAndFueLeidaFalse(idUsuario);

        if (notificaciones.isEmpty()) return false;

        for (Notificacion noti : notificaciones) {
            noti.setFueLeida(true);
        }

        notificacionRepository.saveAll(notificaciones);
        return true;
    }
}
