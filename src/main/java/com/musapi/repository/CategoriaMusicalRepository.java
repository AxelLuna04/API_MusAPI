/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.musapi.repository;

import com.musapi.model.CategoriaMusical;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author luisp
 */
public interface CategoriaMusicalRepository extends JpaRepository<CategoriaMusical, Integer> {
    //boolean existsByNombreCategoria (String nombreCategoria);
}