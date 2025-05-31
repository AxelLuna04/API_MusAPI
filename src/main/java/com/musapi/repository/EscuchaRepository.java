/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.repository;

import com.musapi.model.Escucha;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author axell
 */
public interface EscuchaRepository extends JpaRepository<Escucha, Integer>{
    @Query("SELECT DISTINCT e.usuario.idUsuario FROM Escucha e WHERE e.cancion.idCancion IN :ids")
    Set<Integer> findOyentesByCancionIds(@Param("ids") Set<Integer> ids);
    List<Escucha> findByUsuario_IdUsuarioAndFechaEscuchaBetween(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin);
    List<Escucha> findByFechaEscuchaBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
