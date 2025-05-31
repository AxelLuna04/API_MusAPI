/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.controller;

import com.musapi.dto.ContenidoDTO;
import com.musapi.dto.RespuestaDTO;
import com.musapi.service.ContenidoService;
import com.musapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author axell
 */
@RestController
@RequestMapping("/api/admin")
public class AdministradorController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ContenidoService contenidoService;
    
    @DeleteMapping("/{id}/eliminar-usuario")
    public ResponseEntity<RespuestaDTO<Void>> eliminarUsuario(
            @PathVariable Integer id,
            @RequestParam String motivo) {
        try {
            usuarioService.eliminarUsuario(id, motivo);
            return ResponseEntity.ok(new RespuestaDTO<>("Usuario eliminado correctamente.", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RespuestaDTO<>(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RespuestaDTO<>("Error al eliminar el usuario.", null));
        }
    }
    
    @DeleteMapping("/eliminar-contenido")
    public ResponseEntity<RespuestaDTO<Void>> eliminarContenido(@RequestBody ContenidoDTO contenidoDTO) {
        try {
            contenidoService.eliminarContenido(contenidoDTO);
            return ResponseEntity.ok(new RespuestaDTO<>("Contenido eliminado correctamente.", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RespuestaDTO<>(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RespuestaDTO<>("Error al eliminar el contenido.", null));
        }
    }

}
