/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.service;

import com.musapi.dto.NotificacionDTO;
import com.musapi.model.Notificacion;
import com.musapi.repository.NotificacionRepository;
import java.util.List;
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

    public List<NotificacionDTO> obtenerNotificacionesPendientes(Integer idUsuario) {
        List<Notificacion> notificaciones = notificacionRepository
                .findByUsuario_IdUsuarioAndFueLeidaFalse(idUsuario);

        return notificaciones.stream()
                .map(n -> new NotificacionDTO(n.getIdNotificacion(), n.getMensaje(), n.getFechaEnvio().toString()))
                .collect(Collectors.toList());
    }
}
