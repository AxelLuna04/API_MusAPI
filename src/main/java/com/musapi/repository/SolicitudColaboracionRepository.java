/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.repository;

import com.musapi.model.Cancion;
import com.musapi.model.SolicitudColaboracion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author axell
 */
public interface SolicitudColaboracionRepository extends JpaRepository<SolicitudColaboracion, Integer>{
    SolicitudColaboracion findByIdNotificacion(Integer idNotificacion);
    List<SolicitudColaboracion> findByCancion(Cancion cancion);
}
