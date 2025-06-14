/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.service;

import ch.qos.logback.core.util.SystemInfo;
import com.musapi.dto.InfoAlbumDTO;
import com.musapi.dto.AlbumDTO;
import com.musapi.dto.BusquedaAlbumDTO;
import com.musapi.dto.BusquedaCancionDTO;
import com.musapi.model.Album;
import com.musapi.model.Cancion;
import com.musapi.model.PerfilArtista;
import com.musapi.model.Usuario;
import com.musapi.repository.AlbumRepository;
import com.musapi.repository.PerfilArtistaRepository;
import com.musapi.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private CancionService cancionService;
    
    public List<BusquedaAlbumDTO> buscarAlbumesPorNombre(String texto){
        List<Album> albumesEncontrados = albumRepository.findByNombreContainingIgnoreCaseAndEstado(texto, "publico");
        return albumesEncontrados.stream()
                .map(album -> {
                    String nombreArtistaAlbum = album.getPerfilArtista().getUsuario().getNombreUsuario();
                    
                    List<BusquedaCancionDTO> cancionesDeAlbum = album.getCanciones().stream()
                            .map(cancion -> {
                                String nombreArtistas = cancion.getPerfilArtista_CancionList().isEmpty()
                                        ? null
                                        : cancion.getPerfilArtista_CancionList().stream()
                                            .map(pac -> pac.getPerfilArtista().getUsuario().getNombreUsuario())
                                            .collect(Collectors.joining(", "));
                                
                                return new BusquedaCancionDTO(
                                        cancion.getIdCancion(),
                                        cancion.getNombre(),
                                        cancion.getDuracion().toString(),
                                        cancion.getUrlArchivo(),
                                        cancion.getUrlFoto(),
                                        nombreArtistas,
                                        cancion.getFechaPublicacion().toString(),
                                        cancion.getAlbum().getNombre(),
                                        cancion.getCategoriaMusical().getNombre()
                                );
                            })
                            .collect(Collectors.toList());
                    
                    return new BusquedaAlbumDTO(
                        album.getIdAlbum(),
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

        Usuario usuario = usuarioRepository.findByIdUsuario(albumDTO.getIdUsuario());
        PerfilArtista perfil = perfilArtistaRepository.findByUsuario(usuario);
        System.out.println("Nombre artista: " + perfil.getUsuario().getNombreUsuario());
                
        Album album = new Album();

        if (albumDTO.getFoto() != null) {
            String carpeta = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "fotos-albumes";
            File directorio = new File(carpeta);
            
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            String nombreArchivo = "foto_" + perfil.getIdPerfilArtista() + "_" + albumDTO.getNombre() + "_" + System.currentTimeMillis() + ".jpg";
            File destino = new File(directorio, nombreArchivo);
            
            try {
                albumDTO.getFoto().transferTo(destino);
                album.setUrlFoto("/uploads/fotos-albumes/" + nombreArchivo);
            } catch (IOException e) {
                throw new IllegalArgumentException("Error al guardar la imagen.");
            }
        }
        album.setNombre(albumDTO.getNombre());
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

    public List<InfoAlbumDTO> obtenerInfoAlbumesPendientesPorArtista(int idPerfilArtista) {
        List<Album> albumesPendientes = albumRepository.findByEstadoAndPerfilArtista_IdPerfilArtista("pendiente", idPerfilArtista);
        
        return albumesPendientes.stream().map(album -> {
          InfoAlbumDTO dto = new InfoAlbumDTO();
          dto.setIdAlbum(album.getIdAlbum());
            dto.setNombreArtista(album.getPerfilArtista().getUsuario().getNombreUsuario());
            dto.setNombre(album.getNombre());
            dto.setUrlFoto(album.getUrlFoto());
            //dto.setFechaPublicacion(album.getFechaPublicacion().toString());
            return dto;
        }).collect(Collectors.toList());
    }
    
    public List<BusquedaAlbumDTO> obtenerAlbumesPorArtista(int idPerfilArtista) {
        List<Album> albumesPublicos = albumRepository.findByEstadoAndPerfilArtista_IdPerfilArtista("publico", idPerfilArtista);
        
        return albumesPublicos.stream().map(album -> {
            BusquedaAlbumDTO dto = new BusquedaAlbumDTO();
            dto.setIdAlbum(album.getIdAlbum());
            dto.setNombreAlbum(album.getNombre());
            dto.setNombreArtista(album.getPerfilArtista().getUsuario().getNombre());
            dto.setFechaPublicacion(album.getFechaPublicacion().toString());
            dto.setUrlFoto(album.getUrlFoto());
            dto.setCanciones(cancionService.obtenerCancionesPorIdAlbum(album.getIdAlbum()));
            return dto;
        }).collect(Collectors.toList());
    }
    
    public void publicarAlbum(int idAlbum) {
        Album album = albumRepository.findById(idAlbum)
            .orElseThrow(() -> new EntityNotFoundException("Álbum no encontrado"));
        
        album.setEstado("publico"); 
        album.setFechaPublicacion(LocalDate.now());

        
        for (Cancion cancion : album.getCanciones()) {
            cancion.setEstado("publica");
            cancion.setFechaPublicacion(LocalDate.now());
        }

        albumRepository.save(album);
    }
}
