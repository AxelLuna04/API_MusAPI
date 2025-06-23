/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.controller;

import com.musapi.dto.NotificacionDTO;
import com.musapi.service.NotificacionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author axell
 */
@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {
    @Autowired
    private NotificacionService notificacionService;

    @GetMapping("/pendientes/{idUsuario}")
    public List<NotificacionDTO> obtenerNotificacionesPendientes(@PathVariable Integer idUsuario) {
        return notificacionService.obtenerNotificacionesPendientes(idUsuario);
    }
    
    @PutMapping("/marcar-leida/{idNotificacion}")
    public ResponseEntity<String> marcarComoLeida(@PathVariable Integer idNotificacion) {
        boolean exito = notificacionService.marcarNotificacionComoLeida(idNotificacion);

        if (exito) {
            return ResponseEntity.ok("Notificación marcada como leída.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notificación no encontrada.");
        }
    }
    
    @PutMapping("/marcar-todas-leidas/{idUsuario}")
    public ResponseEntity<String> marcarTodasComoLeidas(@PathVariable Integer idUsuario) {
        boolean exito = notificacionService.marcarTodasComoLeidas(idUsuario);

        if (exito) {
            return ResponseEntity.ok("Todas las notificaciones fueron marcadas como leídas.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay notificaciones pendientes.");
        }
    }


}
