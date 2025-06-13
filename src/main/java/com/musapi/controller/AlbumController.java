/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.controller;

import com.musapi.dto.AlbumDTO;
import com.musapi.dto.BusquedaAlbumDTO;
import com.musapi.dto.InfoAlbumDTO;
import com.musapi.dto.RespuestaDTO;
import com.musapi.service.AlbumService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author axell
 */
@RestController
@RequestMapping("/api/albumes")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    
    @GetMapping("/buscar")
    public ResponseEntity<RespuestaDTO<List<BusquedaAlbumDTO>>> buscarAlbumes(@RequestParam("texto") String texto) {
        try {
            List<BusquedaAlbumDTO> resultados = albumService.buscarAlbumesPorNombre(texto);
            if (resultados.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RespuestaDTO<>("No se encontraron álbumes", resultados));
            }
            return ResponseEntity.ok(new RespuestaDTO<>("Álbumes encontrados exitosamente", resultados));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new RespuestaDTO<>("Ocurrió un error al buscar los álbumes", null));
        }
    }
    
    @PostMapping("/crear")
    public ResponseEntity<RespuestaDTO<Void>> crearAlbum(@ModelAttribute AlbumDTO albumDTO) {
        try {
            albumService.crearAlbum(albumDTO);
            return ResponseEntity.ok(new RespuestaDTO<>("Álbum creado exitosamente.", null));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new RespuestaDTO<>(ex.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RespuestaDTO<>("Error al crear el álbum: " + e.getMessage(), null));
        }
    }

    @GetMapping("/publicos")
    public ResponseEntity<RespuestaDTO<List<InfoAlbumDTO>>> obtenerInfoAlbumesPublicos() {
        try {
            List<InfoAlbumDTO> albumes = albumService.obtenerInfoAlbumesPublicos();

            if (albumes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RespuestaDTO<>("No hay álbumes públicos disponibles", albumes));
            }

            return ResponseEntity.ok(new RespuestaDTO<>("Álbumes públicos recuperados exitosamente", albumes));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new RespuestaDTO<>("Ocurrió un error al recuperar los álbumes públicos", null));
        }
    }
    
    @GetMapping("/pendientes")
    public ResponseEntity<RespuestaDTO<List<InfoAlbumDTO>>> obtenerInfoAlbumesPrivados(@RequestParam("idPerfilArtista") int idPerfilArtista) {
        try {
            List<InfoAlbumDTO> albumes = albumService.obtenerInfoAlbumesPendientesPorArtista(idPerfilArtista);
            if (albumes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new RespuestaDTO<>("No existen álbumes pendientes", albumes));
            }
            return ResponseEntity.ok(new RespuestaDTO<>("Álbumes pendientes recuperados exitosamente", albumes));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RespuestaDTO<>("Ocurrió un error al recuperar los álbumes pendientes", null));
        }
    }
    
    @GetMapping("/artista")
    public ResponseEntity<RespuestaDTO<List<BusquedaAlbumDTO>>> obtenerAlbumes(@RequestParam("idPerfilArtista") int idPerfilArtista) {
        try {
            List<BusquedaAlbumDTO> albumes = albumService.obtenerAlbumesPorArtista(idPerfilArtista);
            if (albumes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new RespuestaDTO<>("No existen álbumes pendientes", albumes));
            }
            return ResponseEntity.ok(new RespuestaDTO<>("Álbumes pendientes recuperados exitosamente", albumes));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RespuestaDTO<>("Ocurrió un error al recuperar los álbumes pendientes: "+e.getMessage(), null));
        }
    }

}
