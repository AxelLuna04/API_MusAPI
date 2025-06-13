/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.service;

import com.musapi.dto.BusquedaCancionDTO;
import com.musapi.dto.CategoriaMusicalDTO;
import com.musapi.model.Cancion;
import com.musapi.model.CategoriaMusical;
import com.musapi.repository.CategoriaMusicalRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author luisp
 */
@Service
public class CategoriaMusicalService {
    @Autowired
    private CategoriaMusicalRepository categoriaMusicalRepository;
    
    public List<CategoriaMusicalDTO> obtenerCategorias(){
        List<CategoriaMusical> categoriasEncontradas = categoriaMusicalRepository.findAll();
        
        return categoriasEncontradas.stream()
                .map(categoria -> {
                    return new CategoriaMusicalDTO(
                        categoria.getIdCategoriaMusical(),
                        categoria.getNombre(),
                        categoria.getDescripcion()
                    );
                            
                }).collect(Collectors.toList());
    }
}
