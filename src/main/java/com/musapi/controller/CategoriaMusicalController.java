/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.controller;

import com.musapi.model.CategoriaMusical;
import com.musapi.repository.CategoriaMusicalRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @PostMapping ("/registrar")
    public CategoriaMusical registrarCategoriaMusical (@RequestBody CategoriaMusical categoriaMusical) {
        return categoriaMusicalRepository.save(categoriaMusical);
    }
    
    @GetMapping
    public List<CategoriaMusical> obtenerUsuarios() {
        return categoriaMusicalRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaMusical> obtenerCategoriaMusicalPorId(@PathVariable Integer id) {
        Optional<CategoriaMusical> categoriaMusical = categoriaMusicalRepository.findById(id);
        
        if(categoriaMusical.isPresent()) {
            return ResponseEntity.ok(categoriaMusical.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaMusical> actualizarCategoriaMusical(@PathVariable Integer id, @RequestBody CategoriaMusical categoriaMusicalActualizada) {
        return categoriaMusicalRepository.findById(id)
                .map(categoriaMusicalExistente -> {
                    if(categoriaMusicalActualizada.getNombre() != null) {
                        categoriaMusicalExistente.setNombre(categoriaMusicalActualizada.getNombre());
                    }
                    if(categoriaMusicalActualizada.getDescripcion()!= null) {
                        categoriaMusicalExistente.setDescripcion(categoriaMusicalActualizada.getDescripcion());
                    }
                    
                    return ResponseEntity.ok(categoriaMusicalRepository.save(categoriaMusicalExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCategoriaMusical(@PathVariable Integer id) {
        Optional<CategoriaMusical> categoriaMusical = categoriaMusicalRepository.findById(id);
        
        if(categoriaMusical.isPresent()) {
            categoriaMusicalRepository.delete(categoriaMusical.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
