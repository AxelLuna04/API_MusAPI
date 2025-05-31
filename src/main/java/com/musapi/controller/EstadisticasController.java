/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.controller;

import com.musapi.dto.CancionMasEscuchadaDTO;
import com.musapi.dto.EstadisticasContenidoSubidoDTO;
import com.musapi.dto.EstadisticasNumeroUsuariosDTO;
import com.musapi.dto.EstadisticasPersonalesDTO;
import com.musapi.dto.RespuestaDTO;
import com.musapi.service.EstadisticasService;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author axell
 */
@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {
    
    @Autowired
    private EstadisticasService estadisticasService;
    
    @GetMapping("/contenidoSubido")
    public ResponseEntity<RespuestaDTO<EstadisticasContenidoSubidoDTO>> obtenerEstadisticasContenidoSubido(
            @RequestParam Integer idPerfilArtista,
            @RequestParam String tipoContenido
    ) {
        try {
            EstadisticasContenidoSubidoDTO resultado = estadisticasService.obtenerEstadisticas(idPerfilArtista, tipoContenido);
            RespuestaDTO<EstadisticasContenidoSubidoDTO> respuesta = new RespuestaDTO<>("Estadísticas obtenidas correctamente", resultado);
            return ResponseEntity.ok(respuesta);

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(new RespuestaDTO<>(e.getMessage(), null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new RespuestaDTO<>(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new RespuestaDTO<>("Error interno del servidor", null));
        }
    }
    
    @GetMapping("/personales")
    public ResponseEntity<RespuestaDTO<EstadisticasPersonalesDTO>> obtenerEstadisticasPersonales(
            @RequestParam Integer idUsuario,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin
    ) {
        try {
            EstadisticasPersonalesDTO resultado = estadisticasService.obtenerEstadisticasPersonales(idUsuario, fechaInicio, fechaFin);
            RespuestaDTO<EstadisticasPersonalesDTO> respuesta = new RespuestaDTO<>("Estadísticas personales obtenidas correctamente", resultado);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new RespuestaDTO<>("Error interno del servidor", null));
        }
    }

    @GetMapping("/numeroUsuarios")
    public ResponseEntity<RespuestaDTO<EstadisticasNumeroUsuariosDTO>> obtenerConteoUsuariosArtistas() {
        try {
            EstadisticasNumeroUsuariosDTO resultado = estadisticasService.obtenerConteoUsuariosYArtistas();
            return ResponseEntity.ok(new RespuestaDTO<>("Conteo obtenido correctamente", resultado));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new RespuestaDTO<>("Error interno del servidor", null));
        }
    }

    @GetMapping("/topArtistas")
    public ResponseEntity<RespuestaDTO<List<ArtistaMasEscuchadoDTO>>> obtenerTopArtistas(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        try {
            List<ArtistaMasEscuchadoDTO> resultado = estadisticasService.obtenerTop10ArtistasMasEscuchados(fechaInicio, fechaFin);
            return ResponseEntity.ok(new RespuestaDTO<>("Top artistas obtenido correctamente", resultado));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new RespuestaDTO<>("Error interno del servidor", null));
        }
    }
    
    @GetMapping("/topCanciones")
    public ResponseEntity<RespuestaDTO<List<CancionMasEscuchadaDTO>>> obtenerTopCanciones(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        try {
            List<CancionMasEscuchadaDTO> resultado = estadisticasService.obtenerTop10CancionesMasEscuchadas(fechaInicio, fechaFin);
            return ResponseEntity.ok(new RespuestaDTO<>("Top canciones obtenido correctamente", resultado));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new RespuestaDTO<>("Error interno del servidor", null));
        }
    }
}
