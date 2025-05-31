/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.controller;

import com.musapi.dto.EscuchaDTO;
import com.musapi.dto.RespuestaDTO;
import com.musapi.service.EscuchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author axell
 */
@RestController
@RequestMapping("/api/escucha")
public class EscuchaController {
    
    @Autowired
    private EscuchaService escuchaService;

    @PostMapping("/registrar")
    public ResponseEntity<RespuestaDTO<Void>> registrarEscucha(@RequestBody EscuchaDTO escuchaDTO) {
        try {
            escuchaService.registrarEscucha(escuchaDTO);
            return ResponseEntity.ok(new RespuestaDTO<>("Escucha registrada correctamente.", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RespuestaDTO<>(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RespuestaDTO<>("Error al registrar la escucha.", null));
        }
    }
}
