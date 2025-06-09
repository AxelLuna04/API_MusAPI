/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.musapi.repository;

import com.musapi.model.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author jarly
 */
public interface UsuarioRepository  extends JpaRepository<Usuario, Integer> {
    boolean existsByCorreo(String correo);
    boolean existsByNombreUsuario(String nombreUsuario);
    Usuario findByCorreo(String correo);
    List<Usuario> findByEsArtistaTrueAndNombreUsuarioContainingIgnoreCase(String nombreUsuario);
    Usuario findByIdUsuario(Integer idUsuario);
    Usuario findByIdUsuarioAndEsArtistaTrue(Integer idUsuario);
}
