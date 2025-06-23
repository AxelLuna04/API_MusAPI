/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.repository;

import com.musapi.model.Cancion;
import com.musapi.model.ListaDeReproduccion_Cancion;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author axell
 */
public interface ListaDeReproduccion_CancionRepository extends JpaRepository<ListaDeReproduccion_Cancion, Integer>{
    @Query("SELECT rel FROM ListaDeReproduccion_Cancion rel WHERE rel.cancion.idCancion IN :ids")
    List<ListaDeReproduccion_Cancion> findByCancionIds(@Param("ids") Set<Integer> ids); 
    @Transactional
    void deleteByCancion(Cancion cancion);
}
