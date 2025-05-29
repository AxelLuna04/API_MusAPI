/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.repository;

import com.musapi.model.Album;
import com.musapi.model.Cancion;
import com.musapi.model.ContenidoGuardado;
import com.musapi.model.ListaDeReproduccion;
import com.musapi.model.PerfilArtista;
import com.musapi.model.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author axell
 */
public interface ContenidoGuardadoRepository extends JpaRepository<ContenidoGuardado, Integer>{
    ContenidoGuardado findByUsuarioAndAlbum(Usuario usuario, Album album);
    ContenidoGuardado findByUsuarioAndPerfilArtista(Usuario usuario, PerfilArtista artista);
    ContenidoGuardado findByUsuarioAndCancion(Usuario usuario, Cancion cancion);
    ContenidoGuardado findByUsuarioAndListaDeReproduccion(Usuario usuario, ListaDeReproduccion lista);
    List<ContenidoGuardado> findByUsuarioAndCancionIsNotNull(Usuario usuario);
    List<ContenidoGuardado> findByUsuarioAndListaDeReproduccionIsNotNull(Usuario usuario);
    List<ContenidoGuardado> findByUsuarioAndAlbumIsNotNull(Usuario usuario);
    List<ContenidoGuardado> findByUsuarioAndPerfilArtistaIsNotNull(Usuario usuario);
}
