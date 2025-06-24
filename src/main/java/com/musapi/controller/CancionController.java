/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.controller;

import com.musapi.dto.BusquedaCancionDTO;
import com.musapi.dto.CancionDTO;
import com.musapi.dto.RespuestaDTO;
import com.musapi.service.CancionService;
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
@RequestMapping("/api/canciones")

public class CancionController {

    @Autowired
    private CancionService cancionService;

    @GetMapping("/buscar")
    public ResponseEntity<RespuestaDTO<List<BusquedaCancionDTO>>> buscarCanciones(@RequestParam("texto") String texto) {
        List<BusquedaCancionDTO> resultados = cancionService.buscarCancionesPorNombre(texto);

        if (resultados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RespuestaDTO<>("No se encontraron canciones", resultados));
        }

        return ResponseEntity.ok(new RespuestaDTO<>("Canciones encontradas exitosamente", resultados));
    }

    @PostMapping("/subir")
    public ResponseEntity<RespuestaDTO<String>> subirCancion(@ModelAttribute CancionDTO cancionDTO) {
        String resultado = cancionService.SubirCancion(cancionDTO);
        if (resultado.equals("Cancion registrada exitosamente.")) {
            return ResponseEntity.ok(new RespuestaDTO<>(resultado, null));
        } else {
            return ResponseEntity.badRequest().body(new RespuestaDTO<>(resultado, null));
        }
    }

    @PutMapping("/{id}/editar-cancion")
    public ResponseEntity<RespuestaDTO<String>> editarCancion(
            @PathVariable Integer idCancion,
            @RequestParam String nombre,
            @RequestParam MultipartFile archivoCancion,
            @RequestParam MultipartFile urlFoto,
            @RequestParam String duracionStr,
            @RequestParam Integer idCategoriaMusical,
            @RequestParam Integer idAlbum,
            @RequestParam Integer posicionEnAlbum
    ) {
        CancionDTO cancionDTO = new CancionDTO(nombre, archivoCancion, urlFoto, duracionStr, idCategoriaMusical, idAlbum, posicionEnAlbum, null);
        cancionService.editarCancion(idCancion, cancionDTO);
        return ResponseEntity.ok(new RespuestaDTO<>("Cancion editada con éxito.", null));
    }

    @GetMapping("/album/{idAlbum}/canciones")
    public ResponseEntity<RespuestaDTO<List<BusquedaCancionDTO>>> obtenerCancionesPorAlbum(@PathVariable Integer idAlbum) {
        List<BusquedaCancionDTO> canciones = cancionService.obtenerCancionesPorIdAlbum(idAlbum);

        if (canciones.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RespuestaDTO<>("No se encontraron canciones para este álbum", canciones));
        }

        return ResponseEntity.ok(new RespuestaDTO<>("Canciones del álbum obtenidas exitosamente", canciones));
    }

    @GetMapping("/artista")
    public ResponseEntity<RespuestaDTO<List<BusquedaCancionDTO>>> obtenerSencillos(@RequestParam("idPerfilArtista") int idPerfilArtista) {
        List<BusquedaCancionDTO> sencillos = cancionService.obtenerSencillosPorIdArtista(idPerfilArtista);
        if (sencillos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RespuestaDTO<>("No existen sencillos", sencillos));
        }
        return ResponseEntity.ok(new RespuestaDTO<>("Sencillos recuperados exitosamente", sencillos));
    }

    @DeleteMapping("/{idCancion}/eliminar")
    public ResponseEntity<RespuestaDTO<String>> eliminarCancion(@PathVariable Integer idCancion) {
        String resultado = cancionService.eliminarCancion(idCancion);
        return ResponseEntity.ok(new RespuestaDTO<>(resultado, null));
    }

}
