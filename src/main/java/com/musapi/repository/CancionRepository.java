/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.repository;

import com.musapi.model.Album;
import com.musapi.model.Cancion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author axell
 */
public interface CancionRepository extends JpaRepository<Cancion, Integer>{
    Cancion findByIdCancion(Integer idCancion);
    List<Cancion> findByAlbum(Album album);
    
    @Query("SELECT c FROM Cancion c " +
            "JOIN c.perfilArtista_CancionList pac " +
            "JOIN pac.perfilArtista pa " +
            "WHERE c.estado = :estado AND pa.idPerfilArtista = :idPerfilArtista " +
            "AND c.urlFoto IS NOT NULL")
     List<Cancion> findByEstadoAndArtistaId(
         @Param("estado") String estado, 
         @Param("idPerfilArtista") int idPerfilArtista
     );
    
    @Query("SELECT c FROM Cancion c " +
            "WHERE c.estado = :estado AND LOWER(c.nombre) " +
            "LIKE LOWER(CONCAT('%', :nombreCancion, '%'))")
    List<Cancion> findByEstadoAndNombreContainingIgnoreCase(
        @Param("estado") String estado,
        @Param("nombreCancion") String nombreCancion
    );
}
