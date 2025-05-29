/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.controller;

import com.musapi.dto.RespuestaDTO;
import com.musapi.service.SolicitudDeColaboracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author axell
 */
@RestController
@RequestMapping("/api/solicitudesDeColaboracion")
public class SolicitudColaboracionController {
    
    @Autowired
    private SolicitudDeColaboracionService solicitudService;
    
    @PutMapping("/{idNotificacion}/responder")
    public ResponseEntity<RespuestaDTO<String>> responderSolicitudColaboracion(
        @PathVariable Integer idNotificacion,
        @RequestParam("respuesta") String respuesta
    ) {
        try {
            String resultado = solicitudService.responderSolicitudColaboracion(idNotificacion, respuesta);
            return ResponseEntity.ok(new RespuestaDTO<>(resultado, null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new RespuestaDTO<>(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RespuestaDTO<>("Error interno al procesar la solicitud.", null));
        }
    }

}
