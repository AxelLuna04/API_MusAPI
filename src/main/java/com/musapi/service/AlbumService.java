/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.service;

import com.musapi.dto.InfoAlbumDTO;
import com.musapi.dto.AlbumDTO;
import com.musapi.dto.BusquedaAlbumDTO;
import com.musapi.dto.BusquedaCancionDTO;
import com.musapi.model.Album;
import com.musapi.model.PerfilArtista;
import com.musapi.repository.AlbumRepository;
import com.musapi.repository.PerfilArtistaRepository;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
    
    @Autowired
    private PerfilArtistaRepository perfilArtistaRepository;
    
    public List<BusquedaAlbumDTO> buscarAlbumesPorNombre(String texto){
        List<Album> albumesEncontrados = albumRepository.findByNombreContainingIgnoreCase(texto);
        
        return albumesEncontrados.stream()
                .map(album -> {
                    String nombreArtistaAlbum = album.getPerfilArtista().getUsuario().getNombreUsuario();
                    
                    List<BusquedaCancionDTO> cancionesDeAlbum = album.getCanciones().stream()
                            .map(cancion -> {
                                String nombreArtistaCancion = cancion.getPerfilArtista_CancionList().get(0).getPerfilArtista().getUsuario().getNombreUsuario();
                                
                                return new BusquedaCancionDTO(
                                        cancion.getNombre(),
                                        cancion.getDuracion().toString(),
                                        cancion.getUrlArchivo(),
                                        cancion.getUrlFoto(),
                                        nombreArtistaCancion,
                                        cancion.getFechaPublicacion().toString(),
                                        cancion.getAlbum().getNombre(),
                                        cancion.getCategoriaMusical().getNombre()
                                );
                            })
                            .collect(Collectors.toList());
                    
                    return new BusquedaAlbumDTO(
                        album.getNombre(),
                        nombreArtistaAlbum,
                        album.getFechaPublicacion().toString(),
                        album.getUrlFoto(),
                        cancionesDeAlbum
                    );
                    
                })
                .collect(Collectors.toList());
    }
    
    public void crearAlbum(AlbumDTO albumDTO) {
        LocalDate fechaPublicacion;
        try {
            fechaPublicacion = LocalDate.parse(albumDTO.getFechaPublicacion());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use yyyy-MM-dd.");
        }

        PerfilArtista perfil = perfilArtistaRepository.findById(albumDTO.getIdPerfilArtista())
                .orElseThrow(() -> new IllegalArgumentException("PerfilArtista no encontrado con id: " + albumDTO.getIdPerfilArtista()));

        if (albumDTO.getFoto() != null) {
            String nombreArchivo = "foto_" + albumDTO.getIdPerfilArtista() + albumDTO.getNombre() + "_" + System.currentTimeMillis() + ".jpg";
            String rutaDestino = "uploads/fotos-albumes/" + nombreArchivo;
            java.io.File destino = new java.io.File(rutaDestino);
            
            destino.getParentFile().mkdirs();
            
            try {
                albumDTO.getFoto().transferTo(destino);
                perfil.setUrlFoto("/" + rutaDestino);
            } catch (IOException e) {
                throw new IllegalArgumentException("Error al guardar la imagen.");
            }
        }

        Album album = new Album();
        album.setNombre(albumDTO.getNombre());
        album.setFechaPublicacion(fechaPublicacion);
        album.setPerfilArtista(perfil);
        album.setEstado("pendiente");

        albumRepository.save(album);
    }

    public List<InfoAlbumDTO> obtenerInfoAlbumesPublicos() {
        List<Album> albumesPublicos = albumRepository.findByEstado("publico");

        return albumesPublicos.stream().map(album -> {
            InfoAlbumDTO dto = new InfoAlbumDTO();
            dto.setIdAlbum(album.getIdAlbum());
            dto.setNombreArtista(album.getPerfilArtista().getUsuario().getNombreUsuario());
            dto.setNombre(album.getNombre());
            dto.setUrlFoto(album.getUrlFoto());
            dto.setFechaPublicacion(album.getFechaPublicacion().toString());
            return dto;
        }).collect(Collectors.toList());
    }

}
