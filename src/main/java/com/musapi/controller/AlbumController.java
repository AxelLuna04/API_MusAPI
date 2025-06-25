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
        List<BusquedaAlbumDTO> resultados = albumService.buscarAlbumesPorNombre(texto);
        if (resultados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RespuestaDTO<>("No se encontraron álbumes", resultados));
        }
        return ResponseEntity.ok(new RespuestaDTO<>("Álbumes encontrados exitosamente", resultados));

    }

    @PostMapping("/crear")
    public ResponseEntity<RespuestaDTO<Void>> crearAlbum(@ModelAttribute AlbumDTO albumDTO) {
        albumService.crearAlbum(albumDTO);
        return ResponseEntity.ok(new RespuestaDTO<>("Álbum creado exitosamente.", null));
    }

    @GetMapping("/publicos")
    public ResponseEntity<RespuestaDTO<List<InfoAlbumDTO>>> obtenerInfoAlbumesPublicos() {
        List<InfoAlbumDTO> albumes = albumService.obtenerInfoAlbumesPublicos();

        if (albumes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RespuestaDTO<>("No hay álbumes públicos disponibles", albumes));
        }

        return ResponseEntity.ok(new RespuestaDTO<>("Álbumes públicos recuperados exitosamente", albumes));
    }

    @GetMapping("/pendientes")
    public ResponseEntity<RespuestaDTO<List<InfoAlbumDTO>>> obtenerInfoAlbumesPrivados(@RequestParam("idPerfilArtista") int idPerfilArtista) {
        List<InfoAlbumDTO> albumes = albumService.obtenerInfoAlbumesPendientesPorArtista(idPerfilArtista);
        if (albumes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RespuestaDTO<>("No existen álbumes pendientes", albumes));
        }
        return ResponseEntity.ok(new RespuestaDTO<>("Álbumes pendientes recuperados exitosamente", albumes));
    }

    @GetMapping("/artista")
    public ResponseEntity<RespuestaDTO<List<BusquedaAlbumDTO>>> obtenerAlbumesPublicos(@RequestParam("idPerfilArtista") int idPerfilArtista) {
        List<BusquedaAlbumDTO> albumes = albumService.obtenerAlbumesPorArtista(idPerfilArtista);
        if (albumes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RespuestaDTO<>("No existen álbumes públicos", albumes));
        }
        return ResponseEntity.ok(new RespuestaDTO<>("Álbumes públicos recuperados exitosamente", albumes));
    }

    @PutMapping("/publicar/{idAlbum}")
    public ResponseEntity<RespuestaDTO<Void>> publicarAlbum(@PathVariable int idAlbum) {
        albumService.publicarAlbum(idAlbum);
        return ResponseEntity.ok(new RespuestaDTO<>("Álbum y sus canciones publicados exitosamente", null));
    }

    @DeleteMapping("/{idAlbum}/eliminar")
    public ResponseEntity<RespuestaDTO<String>> eliminarAlbum(@PathVariable int idAlbum) {
        String resultado = albumService.eliminarAlbum(idAlbum);
        System.out.println(resultado);
        return ResponseEntity.ok(new RespuestaDTO<>(resultado, null));
    }

    @PutMapping("/editar")
    public ResponseEntity<RespuestaDTO<Void>> editarAlbum(@ModelAttribute AlbumDTO albumDTO) {
        albumService.editarAlbum(albumDTO);
        return ResponseEntity.ok(new RespuestaDTO<>("Álbum editado exitosamente.", null));
    }

}
