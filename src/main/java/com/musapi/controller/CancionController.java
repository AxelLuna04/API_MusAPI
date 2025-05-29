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
import org.springframework.web.bind.annotation.GetMapping;
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
        try {
            List<BusquedaCancionDTO> resultados = cancionService.buscarCancionesPorNombre(texto);

            if (resultados.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RespuestaDTO<>("No se encontraron canciones", resultados));
            }

            return ResponseEntity.ok(new RespuestaDTO<>("Canciones encontradas exitosamente", resultados));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new RespuestaDTO<>("Ocurrió un error al buscar las canciones", null));
        }
    }

    
    @PostMapping("/subir")
    public ResponseEntity<RespuestaDTO<String>> subirCancion(
            @RequestParam("nombre") String nombre,
            @RequestParam("archivoCancion") MultipartFile archivoCancion,
            @RequestParam("foto") MultipartFile foto,
            @RequestParam("duracion") String duracionStr,
            @RequestParam("idCategoriaMusical") Integer idCategoriaMusical,
            @RequestParam("idAlbum") Integer idAlbum,
            @RequestParam("posicionEnAlbum") Integer posicionEnAlbum,
            @RequestParam("idPerfilArtistas") List<Integer> idPerfilArtistas
    ) {
        try {
            CancionDTO cancionDTO = new CancionDTO();
            cancionDTO.setNombre(nombre);
            cancionDTO.setDuracionStr(duracionStr);
            cancionDTO.setArchivoCancion(archivoCancion);
            cancionDTO.setFoto(foto);
            cancionDTO.setIdCategoriaMusical(idCategoriaMusical);
            cancionDTO.setIdAlbum(idAlbum);
            cancionDTO.setPosicionEnAlbum(posicionEnAlbum);
            cancionDTO.setIdPerfilArtistas(idPerfilArtistas);

            String resultado = cancionService.SubirCancion(cancionDTO);

            if (resultado.equals("Cancion registrada exitosamente.")) {
                return ResponseEntity.ok(new RespuestaDTO<>(resultado, null));
            } else {
                return ResponseEntity.badRequest().body(new RespuestaDTO<>(resultado, null));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new RespuestaDTO<>("El sistema falló al conectarse a la base de datos.", null));
        }
    }

    @PutMapping("/{id}/editar-cancion")
    public ResponseEntity<RespuestaDTO<String>> editarCancion(
            @PathVariable Integer idCancion,
            @RequestParam String nombre,
            @RequestParam MultipartFile archivoCancion,
            @RequestParam MultipartFile foto,
            @RequestParam String duracionStr,
            @RequestParam Integer idCategoriaMusical,
            @RequestParam Integer idAlbum,
            @RequestParam Integer posicionEnAlbum
    ){
        CancionDTO cancionDTO = new CancionDTO(nombre, archivoCancion, foto, duracionStr, idCategoriaMusical, idAlbum, posicionEnAlbum, null);
        try {
            cancionService.editarCancion(idCancion, cancionDTO);
            return ResponseEntity.ok(new RespuestaDTO<>("Cancion editada con éxito.", null));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new RespuestaDTO<>(ex.getMessage(), null));
        }
    }
    
    @GetMapping("/album/{idAlbum}/canciones")
    public ResponseEntity<RespuestaDTO<List<BusquedaCancionDTO>>> obtenerCancionesPorAlbum(@PathVariable Integer idAlbum) {
        try {
            List<BusquedaCancionDTO> canciones = cancionService.obtenerCancionesPorIdAlbum(idAlbum);

            if (canciones.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new RespuestaDTO<>("No se encontraron canciones para este álbum", canciones));
            }

            return ResponseEntity.ok(new RespuestaDTO<>("Canciones del álbum obtenidas exitosamente", canciones));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new RespuestaDTO<>(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RespuestaDTO<>("Error al obtener las canciones del álbum", null));
        }
    }

}
