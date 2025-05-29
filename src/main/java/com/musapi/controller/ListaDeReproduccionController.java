/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.controller;

import com.musapi.dto.BusquedaCancionDTO;
import com.musapi.dto.CreacionListaDeReproduccionDTO;
import com.musapi.dto.ListaDeReproduccionDTO;
import com.musapi.dto.ListaDeReproduccion_CancionDTO;
import com.musapi.dto.RespuestaDTO;
import com.musapi.service.ListaDeReproduccionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            return ResponseEntity.status(500).body(new RespuestaDTO<>("Error interno del servidor", null));
        }
    }
    
    @PostMapping("/agregar-cancion")
    public ResponseEntity<RespuestaDTO<String>> agregarCancion(@RequestBody ListaDeReproduccion_CancionDTO dto) {
        try {
            String mensaje = listaDeReproduccionService.agregarCancionALista(dto);

            if ("Canción agregada correctamente".equals(mensaje)) {
                return ResponseEntity.ok(new RespuestaDTO<>(mensaje, null));
            } else {
                return ResponseEntity.badRequest().body(new RespuestaDTO<>(mensaje, null));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new RespuestaDTO<>(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new RespuestaDTO<>("Error interno del servidor", null));
        }
    }
    
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<RespuestaDTO<List<ListaDeReproduccionDTO>>> obtenerListasPorUsuario(@PathVariable Integer idUsuario) {
        try {
            List<ListaDeReproduccionDTO> listas = listaDeReproduccionService.obtenerListaDeReproduccionPorIdUsuario(idUsuario);
            return ResponseEntity.ok(new RespuestaDTO<>("Listas obtenidas correctamente", listas));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new RespuestaDTO<>(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new RespuestaDTO<>("Error interno del servidor", null));
        }
    }
    
    @GetMapping("/lista/{idListaDeReproduccion}")
    public ResponseEntity<RespuestaDTO<List<BusquedaCancionDTO>>> obtenerCancionesPorIdListaDeReproduccion(@PathVariable Integer idListaDeReproduccion) {
        try {
            List<BusquedaCancionDTO> canciones = listaDeReproduccionService.obtenerCancionesPorIdListaDeReproduccion(idListaDeReproduccion);
            return ResponseEntity.ok(new RespuestaDTO<>("Canciones obtenidas correctamente", canciones));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new RespuestaDTO<>(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new RespuestaDTO<>("Error interno del servidor", null));
        }
    }

}
