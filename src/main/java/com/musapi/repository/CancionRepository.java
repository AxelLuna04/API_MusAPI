/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.repository;

import com.musapi.model.Album;
import com.musapi.model.Cancion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author axell
 */
public interface CancionRepository extends JpaRepository<Cancion, Integer>{
    List<Cancion> findByNombreContainingIgnoreCase(String nombreCancion);
    Cancion findByIdCancion(Integer idCancion);
    List<Cancion> findByAlbum(Album album);
}
