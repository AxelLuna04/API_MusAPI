/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.service;

import com.musapi.dto.ContenidoDTO;
import com.musapi.enums.TipoContenido;
import com.musapi.model.Album;
import com.musapi.model.Cancion;
import com.musapi.model.PerfilArtista;
import com.musapi.model.PerfilArtista_Cancion;
import com.musapi.model.Usuario;
import com.musapi.repository.AlbumRepository;
import com.musapi.repository.CancionRepository;
import com.musapi.repository.PerfilArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author axell
 */
@Service
public class ContenidoService {

    @Autowired
    private AlbumRepository albumRepository;
    
    @Autowired
    private CancionRepository cancionRepository;
    
    @Autowired
    private PerfilArtistaRepository perfilArtistaRepository;
    
    @Autowired
    private CorreoService correoService;

    public void eliminarContenido(ContenidoDTO contenidoDTO) {
        Integer id = contenidoDTO.getIdContenido();
        TipoContenido tipo = contenidoDTO.getTipoContendio();
        String motivo = contenidoDTO.getMotivoEliminacion();

        switch (tipo) {
            case CANCION -> {
                Cancion cancion = cancionRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Canción no encontrada"));
                
                for (PerfilArtista_Cancion relacion : cancion.getPerfilArtista_CancionList()) {
                    PerfilArtista artista = relacion.getPerfilArtista();
                    Usuario usuario = artista.getUsuario();
                    correoService.enviarCorreo(
                            usuario.getCorreo(),
                            "Una de tus canciones ha sido eliminada",
                            "La canción '" + cancion.getNombre() + "' fue eliminada. Motivo: " + motivo
                    );
                }

                cancionRepository.delete(cancion);
            }

            case ALBUM -> {
                Album album = albumRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Álbum no encontrado"));

                PerfilArtista artistaAlbum = album.getPerfilArtista();
                Usuario usuarioAlbum = artistaAlbum.getUsuario();
                correoService.enviarCorreo(
                        usuarioAlbum.getCorreo(),
                        "Uno de tus álbumes ha sido eliminado",
                        "El álbum '" + album.getNombre() + "' fue eliminado. Motivo: " + motivo
                );

                albumRepository.delete(album);
            }

            case ARTISTA -> {
                PerfilArtista perfil = perfilArtistaRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Perfil de artista no encontrado"));

                Usuario usuarioArtista = perfil.getUsuario();
                correoService.enviarCorreo(
                        usuarioArtista.getCorreo(),
                        "Tu perfil de artista ha sido eliminado",
                        "Tu perfil de artista fue eliminado. Motivo: " + motivo
                );

                perfilArtistaRepository.delete(perfil);
            }

            default -> throw new IllegalArgumentException("Tipo de contenido no válido para eliminación.");
        }
    }
}

