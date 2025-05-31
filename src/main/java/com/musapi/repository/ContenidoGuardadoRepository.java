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
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    
    @Query("SELECT DISTINCT cg.usuario.idUsuario FROM ContenidoGuardado cg WHERE cg.cancion.idCancion IN :ids")
    Set<Integer> findUsuariosByCancionIds(@Param("ids") Set<Integer> ids);

    @Query("SELECT DISTINCT cg.usuario.idUsuario FROM ContenidoGuardado cg WHERE cg.album.idAlbum IN :ids")
    Set<Integer> findUsuariosByAlbumIds(@Param("ids") Set<Integer> ids);

}
