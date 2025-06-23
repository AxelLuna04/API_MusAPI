/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.repository;

import com.musapi.model.Cancion;
import com.musapi.model.PerfilArtista_Cancion;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author axell
 */
public interface PerfilArtista_CancionRepository extends JpaRepository<PerfilArtista_Cancion, Integer>{  
    @Transactional
    void deleteByCancion(Cancion cancion);
}
