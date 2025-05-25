/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.service;

import com.musapi.dto.BusquedaAlbumDTO;
import com.musapi.dto.BusquedaCancionDTO;
import com.musapi.model.Album;
import com.musapi.repository.AlbumRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author axell
 */
@Service
public class AlbumService {
    
    @Autowired
    private AlbumRepository albumRepository;
    
    public List<BusquedaAlbumDTO> buscarAlbumesPorNombre(String texto){
        List<Album> albumesEncontrados = albumRepository.findByNombreContainingIgnoreCase(texto);
        
        return albumesEncontrados.stream()
                .map(album -> {
                    String nombreArtistaAlbum = album.getPerfilArtista().getUsuario().getNombreUsuario();
                    
                    List<BusquedaCancionDTO> cancionesDeAlbum = album.getCanciones().stream()
                            .map(cancion -> {
                                String nombreArtistaCancion = cancion.getPerfilArtista_CancionList().get(0).getPerfilArtista().getUsuario().getNombreUsuario();
                                
                                return new BusquedaCancionDTO(
                                        cancion.getIdCancion(),
                                        cancion.getNombre(),
                                        cancion.getDuracion(),
                                        cancion.getArchivo(),
                                        cancion.getFoto(),
                                        nombreArtistaCancion,
                                        cancion.getFechaPublicacion(),
                                        cancion.getAlbum().getNombre()
                                );
                            })
                            .collect(Collectors.toList());
                    
                    return new BusquedaAlbumDTO(
                        album.getNombre(),
                        nombreArtistaAlbum,
                        album.getFechaPublicacion(),
                        album.getFoto(),
                        cancionesDeAlbum
                    );
                    
                })
                .collect(Collectors.toList());
    }
    
}
