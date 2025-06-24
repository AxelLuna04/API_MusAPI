/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.controller;

import com.musapi.dto.CategoriaMusicalDTO;
import com.musapi.dto.RespuestaDTO;
import com.musapi.model.CategoriaMusical;
import com.musapi.repository.CategoriaMusicalRepository;
import com.musapi.service.CategoriaMusicalService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author luisp
 */

@RestController
@RequestMapping("/api/categoriasMusicales")
public class CategoriaMusicalController {

    @Autowired
    private CategoriaMusicalRepository categoriaMusicalRepository;

    @Autowired
    private CategoriaMusicalService categoriaMusicalService;

    @PostMapping("/registrar")
    public CategoriaMusical registrarCategoriaMusical(@RequestBody CategoriaMusical categoriaMusical) {
        return categoriaMusicalRepository.save(categoriaMusical);
    }

    @GetMapping
    public ResponseEntity<RespuestaDTO<List<CategoriaMusicalDTO>>> obtenerCategorias() {
        List<CategoriaMusicalDTO> resultados = categoriaMusicalService.obtenerCategorias();

        if (resultados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RespuestaDTO<>("No se encontraron categorías", resultados));
        }

        return ResponseEntity.ok(new RespuestaDTO<>("Categorias encontradas exitosamente", resultados));

    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaMusicalDTO> obtenerCategoriaMusicalPorId(@PathVariable Integer id) {
        CategoriaMusical categoriaMusical = categoriaMusicalRepository.findByIdCategoriaMusical(id);

        if (categoriaMusical != null) {
            CategoriaMusicalDTO categoriaMusicalDTO = new CategoriaMusicalDTO(categoriaMusical.getIdCategoriaMusical(), categoriaMusical.getNombre(), categoriaMusical.getDescripcion());
            return ResponseEntity.ok(categoriaMusicalDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaMusicalDTO> actualizarCategoriaMusical(@PathVariable Integer id, @RequestBody CategoriaMusical categoriaMusicalActualizada) {
        return categoriaMusicalRepository.findById(id)
                .map(categoriaMusicalExistente -> {
                    if (categoriaMusicalActualizada.getNombre() != null) {
                        categoriaMusicalExistente.setNombre(categoriaMusicalActualizada.getNombre());
                    }
                    if (categoriaMusicalActualizada.getDescripcion() != null) {
                        categoriaMusicalExistente.setDescripcion(categoriaMusicalActualizada.getDescripcion());
                    }

                    CategoriaMusical categoriaActualizada = categoriaMusicalRepository.save(categoriaMusicalExistente);
                    CategoriaMusicalDTO categoriaMusicalDTO = new CategoriaMusicalDTO(
                            categoriaActualizada.getIdCategoriaMusical(),
                            categoriaActualizada.getNombre(),
                            categoriaActualizada.getDescripcion()
                    );

                    return ResponseEntity.ok(categoriaMusicalDTO);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCategoriaMusical(@PathVariable Integer id) {
        Optional<CategoriaMusical> categoriaMusical = categoriaMusicalRepository.findById(id);

        if (categoriaMusical.isPresent()) {
            categoriaMusicalRepository.delete(categoriaMusical.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
