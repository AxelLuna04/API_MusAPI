/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.repository;

import com.musapi.model.Album;
import com.musapi.model.Cancion;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author axell
 */
public interface CancionRepository extends JpaRepository<Cancion, Integer> {

    Cancion findByIdCancion(Integer idCancion);

    List<Cancion> findByAlbum(Album album);

    @Query("SELECT c FROM Cancion c "
            + "JOIN c.perfilArtista_CancionList pac "
            + "JOIN pac.perfilArtista pa "
            + "WHERE c.estado = :estado AND pa.idPerfilArtista = :idPerfilArtista "
            + "AND c.urlFoto IS NOT NULL")
    List<Cancion> findByEstadoAndArtistaId(
            @Param("estado") String estado,
            @Param("idPerfilArtista") int idPerfilArtista
    );

    @Query("SELECT c FROM Cancion c "
            + "WHERE c.estado = :estado AND LOWER(c.nombre) "
            + "LIKE LOWER(CONCAT('%', :nombreCancion, '%'))")
    List<Cancion> findByEstadoAndNombreContainingIgnoreCase(
            @Param("estado") String estado,
            @Param("nombreCancion") String nombreCancion
    );

    @Query("SELECT DISTINCT c as cancion, "
            + "(SELECT COUNT(e) FROM Escucha e WHERE e.cancion = c) as totalEscuchas, "
            + "(SELECT COUNT(cg) FROM ContenidoGuardado cg WHERE cg.cancion = c) as totalGuardados "
            + "FROM Cancion c "
            + "LEFT JOIN c.perfilArtista_CancionList pac "
            + "WHERE c.estado = 'publica' "
            + "AND c.idCancion NOT IN (SELECT eh.cancion.idCancion FROM Escucha eh WHERE eh.usuario.idUsuario = :idUsuario) "
            + "AND ("
            + "     c.categoriaMusical.idCategoriaMusical IN :generosIds "
            + "     OR pac.perfilArtista.idPerfilArtista IN :artistasIds "
            + "     OR c.idCancion IN (SELECT top.idCancion FROM Cancion top LEFT JOIN top.escuchas te GROUP BY top ORDER BY COUNT(te) DESC LIMIT 50) "
            + ") "
            + "ORDER BY totalEscuchas DESC LIMIT 100")
    List<CancionStatDTO> findCandidatosParaRanking(
            @Param("idUsuario") Integer idUsuario,
            @Param("generosIds") List<Integer> generosIds,
            @Param("artistasIds") List<Integer> artistasIds
    );

    @Query("SELECT c FROM Cancion c LEFT JOIN c.escuchas e GROUP BY c ORDER BY COUNT(e) DESC LIMIT 20")
    List<Cancion> findTopCancionesGlobalesSimple();

}
