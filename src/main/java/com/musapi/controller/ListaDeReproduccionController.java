/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.controller;

import com.musapi.dto.CreacionListaDeReproduccionDTO;
import com.musapi.dto.RespuestaDTO;
import com.musapi.service.ListaDeReproduccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author axell
 */

@RestController
@RequestMapping("/api/listasDeReproduccion")
public class ListaDeReproduccionController {
    
    @Autowired
    private ListaDeReproduccionService listaDeReproduccionService;
    
    @PostMapping("/crear")
    public ResponseEntity<RespuestaDTO<String>> crearLista(@RequestBody CreacionListaDeReproduccionDTO dto) {
        try {
            String mensaje = listaDeReproduccionService.crearListaDeReproduccion(dto);

            if ("Lista creada con éxito".equals(mensaje)) {
                return ResponseEntity.ok(new RespuestaDTO<>(mensaje, null));
            } else {
                return ResponseEntity.badRequest().body(new RespuestaDTO<>(mensaje, null));
            }
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new RespuestaDTO<>(ex.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new RespuestaDTO<>("El sistema falló al conectarse a la base de datos.", null));
        }
    }

}
