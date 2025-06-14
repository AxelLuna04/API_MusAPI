/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.musapi.repository;

import com.musapi.model.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 *
 * @author jarly
 */
public interface UsuarioRepository  extends JpaRepository<Usuario, Integer> {
    boolean existsByCorreo(String correo);
    boolean existsByNombreUsuario(String nombreUsuario);
    Usuario findByCorreo(String correo);
    List<Usuario> findByEsArtistaTrueAndNombreUsuarioContainingIgnoreCase(String nombreUsuario);//solo busca por el nombre de usuario
    Usuario findByIdUsuario(Integer idUsuario);
    Usuario findByIdUsuarioAndEsArtistaTrue(Integer idUsuario);
    
    @Query("SELECT u FROM Usuario u WHERE u.esArtista = true AND " +
           "(LOWER(u.nombreUsuario) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
           "LOWER(u.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%')))")
    List<Usuario> buscarArtistasPorNombreOUsuario(@Param("busqueda") String busqueda);
}
