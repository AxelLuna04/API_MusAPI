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
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<RespuestaDTO<List<BusquedaAlbumDTO>>> obtenerAlbumesPublicos(@RequestParam("idPerfilArtista") int idPerfilArtista) {
        try {
            List<BusquedaAlbumDTO> albumes = albumService.obtenerAlbumesPorArtista(idPerfilArtista);
            if (albumes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new RespuestaDTO<>("No existen álbumes públicos", albumes));
            }
            return ResponseEntity.ok(new RespuestaDTO<>("Álbumes públicos recuperados exitosamente", albumes));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RespuestaDTO<>("Ocurrió un error al recuperar los álbumes públicos: "+e.getMessage(), null));
        }
    }

    @PutMapping("/publicar/{idAlbum}")
    public ResponseEntity<RespuestaDTO<Void>> publicarAlbum(@PathVariable int idAlbum) {
        try {
            albumService.publicarAlbum(idAlbum);
            return ResponseEntity.ok(new RespuestaDTO<>("Álbum y sus canciones publicados exitosamente", null));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new RespuestaDTO<>(ex.getMessage(), null));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RespuestaDTO<>("Álbum no encontrado", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RespuestaDTO<>("Error al publicar el álbum: " + e.getMessage(), null));
        }
    }
    
    @DeleteMapping("/{idAlbum}/eliminar")
    public ResponseEntity<RespuestaDTO<String>> eliminarAlbum(@PathVariable int idAlbum) {
        try {
            String resultado = albumService.eliminarAlbum(idAlbum);
            System.out.println(resultado);
            return ResponseEntity.ok(new RespuestaDTO<>(resultado, null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new RespuestaDTO<>(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new RespuestaDTO<>("Error al eliminar el album: " + e.getMessage(), null));
        }
    }
    
    @PutMapping("/editar")
    public ResponseEntity<RespuestaDTO<Void>> editarAlbum(@ModelAttribute AlbumDTO albumDTO) {
        try {
            albumService.editarAlbum(albumDTO);
            return ResponseEntity.ok(new RespuestaDTO<>("Álbum editado exitosamente.", null));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new RespuestaDTO<>(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new RespuestaDTO<>("Error al editar el álbum: " + e.getMessage(), null));
        }
    }

}
