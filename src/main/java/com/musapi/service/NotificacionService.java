/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author axell
 */
@Service
public class NotificacionService {

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
        List<ContenidoGuardado> seguidores = contenidoGuardadoRepository.findByPerfilArtista(perfilArtista);

        for (ContenidoGuardado contenido : seguidores) {
            Usuario usuarioSeguidor = contenido.getUsuario();

            Notificacion notificacion = new Notificacion();
            notificacion.setUsuario(usuarioSeguidor);
            notificacion.setMensaje(mensajeNotificacion);
            notificacion.setFechaEnvio(LocalDate.now());
            notificacion.setFueLeida(false);
            notificacionRepository.save(notificacion);

            String asunto = "Nuevo contenido de " + perfilArtista.getUsuario().getNombreUsuario();
            String cuerpo = mensajeNotificacion;
            correoService.enviarCorreo(usuarioSeguidor.getCorreo(), asunto, cuerpo);
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
