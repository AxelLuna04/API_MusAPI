/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.controller;

import com.musapi.dto.BusquedaAlbumDTO;
import com.musapi.dto.BusquedaArtistaDTO;
import com.musapi.dto.BusquedaCancionDTO;
import com.musapi.dto.ContenidoGuardadoDTO;
import com.musapi.dto.ListaDeReproduccionDTO;
import com.musapi.dto.RespuestaDTO;
import com.musapi.service.ContenidoGuardadoService;
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
@RequestMapping("/api/contenidoGuardado")
public class ContenidoGuardadoController {
    @Autowired 
    private ContenidoGuardadoService contenidoService;
    
    @PostMapping("/guardar")
    public ResponseEntity<RespuestaDTO<String>> guardarContenido(@RequestBody ContenidoGuardadoDTO dto) {
        try {
            String mensaje = contenidoService.guardarContenido(dto);

            if ("Contenido guardado exitosamente".equals(mensaje)) {
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
    
    @GetMapping("/canciones/{idUsuario}")
    public ResponseEntity<RespuestaDTO<List<BusquedaCancionDTO>>> obtenerCancionesGuardadasPorIdUsuario(@PathVariable Integer idUsuario) {
        List<BusquedaCancionDTO> canciones = contenidoService.obtenerCancionesGuardadasPorUsuario(idUsuario);
        return ResponseEntity.ok(new RespuestaDTO<>("Canciones recuperadas", canciones));
    }

    @GetMapping("/listas/{idUsuario}")
    public ResponseEntity<RespuestaDTO<List<ListaDeReproduccionDTO>>> obtenerListasGuardadasPorIdUsuario(@PathVariable Integer idUsuario) {
        List<ListaDeReproduccionDTO> listas = contenidoService.obtenerListasGuardadasPorUsuario(idUsuario);
        return ResponseEntity.ok(new RespuestaDTO<>("Listas recuperadas", listas));
    }

    @GetMapping("/albumes/{idUsuario}")
    public ResponseEntity<RespuestaDTO<List<BusquedaAlbumDTO>>> obtenerAlbumesGuardadosPorIdUsuario(@PathVariable Integer idUsuario) {
        List<BusquedaAlbumDTO> albumes = contenidoService.obtenerAlbumesGuardadosPorUsuario(idUsuario);
        return ResponseEntity.ok(new RespuestaDTO<>("Álbumes recuperados", albumes));
    }

    @GetMapping("/artistas/{idUsuario}")
    public ResponseEntity<RespuestaDTO<List<BusquedaArtistaDTO>>> obtenerArtistasGuardadosPorIdUsuario(@PathVariable Integer idUsuario) {
        List<BusquedaArtistaDTO> artistas = contenidoService.obtenerArtistasGuardadosPorUsuario(idUsuario);
        return ResponseEntity.ok(new RespuestaDTO<>("Artistas recuperados", artistas));
    }

}
