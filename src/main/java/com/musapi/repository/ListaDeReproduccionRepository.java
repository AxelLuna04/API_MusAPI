/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.repository;

import com.musapi.model.ListaDeReproduccion;
import com.musapi.model.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author axell
 */
public interface ListaDeReproduccionRepository extends JpaRepository<ListaDeReproduccion, Integer>{
    ListaDeReproduccion findByIdListaDeReproduccion(Integer idListaDeReproduccion);
    List<ListaDeReproduccion> findByUsuario(Usuario usuario);

}
