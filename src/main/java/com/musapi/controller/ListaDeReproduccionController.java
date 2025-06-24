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
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<RespuestaDTO<String>> crearLista(@ModelAttribute CreacionListaDeReproduccionDTO dto) {
        String mensaje = listaDeReproduccionService.crearListaDeReproduccion(dto);

        if ("Lista creada con éxito".equals(mensaje)) {
            return ResponseEntity.ok(new RespuestaDTO<>(mensaje, null));
        } else {
            return ResponseEntity.badRequest().body(new RespuestaDTO<>(mensaje, null));
        }
    }

    @PostMapping("/agregar-cancion")
    public ResponseEntity<RespuestaDTO<String>> agregarCancion(@RequestBody ListaDeReproduccion_CancionDTO dto) {
        String mensaje = listaDeReproduccionService.agregarCancionALista(dto);

        if ("Canción agregada correctamente".equals(mensaje)) {
            return ResponseEntity.ok(new RespuestaDTO<>(mensaje, null));
        } else {
            return ResponseEntity.badRequest().body(new RespuestaDTO<>(mensaje, null));
        }
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<RespuestaDTO<List<ListaDeReproduccionDTO>>> obtenerListasPorUsuario(@PathVariable Integer idUsuario) {
        List<ListaDeReproduccionDTO> listas = listaDeReproduccionService.obtenerListasDeReproduccionPorIdUsuario(idUsuario);
        return ResponseEntity.ok(new RespuestaDTO<>("Listas obtenidas correctamente", listas));
    }

    @PutMapping("/editar")
    public ResponseEntity<RespuestaDTO<Void>> editarLista(@ModelAttribute CreacionListaDeReproduccionDTO listaDTO) {
        listaDeReproduccionService.editarLista(listaDTO);
        return ResponseEntity.ok(new RespuestaDTO<>("Lista editada exitosamente.", null));
    }

}
