/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.controller;

import com.musapi.dto.EvaluacionArtistaDTO;
import com.musapi.dto.RespuestaDTO;
import com.musapi.service.EvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author axell
 */
@RestController
@RequestMapping("/api/evaluaciones")
public class EvaluacionController {
    
    @Autowired
    private EvaluacionService evaluacionService;
    
    @PostMapping("/registrar")
    public ResponseEntity<RespuestaDTO<String>> registrarEvaluacion(@RequestBody EvaluacionArtistaDTO evaluacionDTO) {
        try {
            String resultado = evaluacionService.EvaluarArtista(evaluacionDTO);

            if (resultado.equals("Evaluacion registrada con exito.")) {
                return ResponseEntity.ok(new RespuestaDTO<>("Operación exitosa", resultado));
            } else {
                return ResponseEntity.badRequest().body(new RespuestaDTO<>("Error en la operación", resultado));
            }
        } catch (Exception ex) {
            return ResponseEntity.status(500)
                .body(new RespuestaDTO<>("El sistema falló al conectarse a la base de datos.", null));
        }
    }

}
