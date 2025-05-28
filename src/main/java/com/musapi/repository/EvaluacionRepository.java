/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.repository;

import com.musapi.model.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author axell
 */
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Integer>{
    Evaluacion findByUsuario_IdUsuarioAndPerfilArtista_IdPerfilArtista(Integer idUsuario, Integer idPerfilArtista);
}
