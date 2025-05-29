/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.service;

import com.musapi.model.Cancion;
import com.musapi.model.Notificacion;
import com.musapi.model.PerfilArtista;
import com.musapi.model.SolicitudColaboracion;
import com.musapi.repository.CancionRepository;
import com.musapi.repository.NotificacionRepository;
import com.musapi.repository.SolicitudColaboracionRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author axell
 */
@Service
public class SolicitudDeColaboracionService {
    
    @Autowired
    private SolicitudColaboracionRepository solicitudRepository;
    
    @Autowired
    private CancionRepository cancionRepository;
    
    @Autowired
    private NotificacionRepository notificacionRepository;
    
    @Transactional
    public String responderSolicitudColaboracion(Integer idNotificacion, String respuesta) {
        SolicitudColaboracion solicitud = solicitudRepository.findByIdNotificacion(idNotificacion);
        
        if (solicitud == null) {
            throw new IllegalArgumentException("Solicitud no encontrada.");
        }

        if (!solicitud.getEstado().equals("pendiente")) {
            throw new IllegalArgumentException("La solicitud ya fue respondida.");
        }

        solicitud.setEstado(respuesta);
        solicitudRepository.save(solicitud);

        Cancion cancion = solicitud.getCancion();

        if (respuesta.equalsIgnoreCase("rechazada")) {
            cancion.setEstado("solicitudRechazada");
            cancionRepository.save(cancion);
            
            PerfilArtista autorPrincipal = cancion.getPerfilArtista_CancionList().get(0).getPerfilArtista();

            Notificacion notificacion = new Notificacion();
            notificacion.setMensaje("Un artista rechazó colaborar en la canción: " + cancion.getNombre());
            notificacion.setFechaEnvio(LocalDate.now());
            notificacion.setFueLeida(false);
            notificacion.setUsuario(autorPrincipal.getUsuario());

            notificacionRepository.save(notificacion);

            return "Solicitud rechazada. La canción no se publicará.";
        }

        List<SolicitudColaboracion> solicitudes = solicitudRepository.findByCancion(cancion);
        boolean todosAceptaron = solicitudes.stream().allMatch(s -> s.getEstado().equals("aceptada"));

        if (todosAceptaron) {
            cancion.setEstado("publica");
            cancion.setFechaPublicacion(LocalDate.now());
            cancionRepository.save(cancion);
            return "Todos los artistas aceptaron. La canción ahora es pública.";
        }

        return "Solicitud aceptada. Esperando respuesta de otros artistas.";
    }

}
