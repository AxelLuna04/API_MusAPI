package com.musapi.controller;

import com.musapi.dto.BusquedaCancionDTO;
import com.musapi.dto.RespuestaDTO;
import com.musapi.service.RecomendacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recomendaciones")
public class RecomendacionController {

    @Autowired
    private RecomendacionService recomendacionService;

    @GetMapping
    public ResponseEntity<RespuestaDTO<List<BusquedaCancionDTO>>> obtenerRecomendaciones(
            @RequestParam("idUsuario") Integer idUsuario) {
        
        try {
            List<BusquedaCancionDTO> recomendaciones = recomendacionService.generarRecomendaciones(idUsuario);

            if (recomendaciones.isEmpty()) {
                return ResponseEntity.ok(new RespuestaDTO<>("No hay suficientes datos para generar recomendaciones aún, explora música nueva.", recomendaciones));
            }

            return ResponseEntity.ok(new RespuestaDTO<>("Recomendaciones generadas exitosamente", recomendaciones));
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new RespuestaDTO<>("Error al generar recomendaciones: " + e.getMessage(), null));
        }
    }
}
