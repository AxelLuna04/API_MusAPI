/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.repository;

import com.musapi.model.PerfilArtista;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author axell
 */
public interface PerfilArtistaRepository extends JpaRepository<PerfilArtista, Integer>{
    PerfilArtista findByIdPerfilArtista(Integer idPerfilArtista);
}
