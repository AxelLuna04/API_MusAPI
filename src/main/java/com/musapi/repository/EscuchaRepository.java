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
    @Query(value = """
                    SELECT COUNT(DISTINCT e.idUsuario) 
                                      FROM Escucha e 
                                      WHERE e.idCancion IN ( 
                                          SELECT pac.idCancion 
                                          FROM PerfilArtista_Cancion pac 
                                          WHERE pac.idPerfilArtista = :idPerfilArtista
                                      ) 
                                      OR e.idCancion IN ( 
                                          SELECT c.idCancion 
                                          FROM Cancion c 
                                          INNER JOIN Album a ON c.idAlbum = a.idAlbum 
                                          WHERE a.idPerfilArtista = :idPerfilArtista
                                      )
                   """, nativeQuery = true)
    int countUsuariosUnicosQueEscucharonArtista(@Param("idPerfilArtista") int idPerfilArtista);
    
    @Query(value = """
        SELECT COUNT(*) 
                FROM ListaDeReproduccion_Cancion lrc
                WHERE lrc.idCancion IN (
                    SELECT idCancion 
                    FROM perfilArtista_Cancion 
                    WHERE idPerfilArtista = :idPerfilArtista
                ) OR lrc.idCancion IN (
                    SELECT c.idCancion 
                    FROM cancion c
                    INNER JOIN album a ON c.idAlbum = a.idAlbum
                    WHERE a.idPerfilArtista = :idPerfilArtista
                )
        """, nativeQuery = true)
    int countAparicionesEnListasDeReproduccion(@Param("idPerfilArtista") int idPerfilArtista);
    //Set<Integer> findOyentesByCancionIds(@Param("ids") Set<Integer> ids);
    List<Escucha> findByUsuario_IdUsuarioAndFechaEscuchaBetween(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin);
    List<Escucha> findByFechaEscuchaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    List<Escucha> findByUsuario_IdUsuario(Integer idUsuario);
}
