/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.service;

import com.musapi.dto.BusquedaArtistaDTO;
import com.musapi.dto.BusquedaCancionDTO;
import com.musapi.dto.EdicionPerfilDTO;
import com.musapi.model.PerfilArtista;
import com.musapi.model.PerfilArtista_Cancion;
import com.musapi.model.Usuario;
import com.musapi.repository.PerfilArtistaRepository;
import com.musapi.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author axell
 */
@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PerfilArtistaRepository perfilArtistaRepository;
    
    @Transactional
    public boolean editarPerfil(Integer idUsuario, EdicionPerfilDTO edicionPerfil) throws Exception{
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(idUsuario);
        if(optionalUsuario.isEmpty())
            throw new Exception("Usuario no encontrado.");
        
        Usuario usuario = optionalUsuario.get();
        
        usuario.setNombre(edicionPerfil.getNombre());
        usuario.setNombreUsuario(edicionPerfil.getNombreUsuario());
        usuario.setPais(edicionPerfil.getPais());
        
        if (usuario.getEsArtista() != null && usuario.getEsArtista()) {
            PerfilArtista perfil = usuario.getPerfilArtista();
            if (perfil != null && edicionPerfil.getDescripcion() != null) {
                perfil.setDescripcion(edicionPerfil.getDescripcion());
                perfilArtistaRepository.save(perfil);
            }
        }
        
        usuarioRepository.save(usuario);
        return true;
    }
    
    public List<BusquedaArtistaDTO> buscarArtistasPorNombreUsuario(String nombreUsuario) {
        List<Usuario> artistas = usuarioRepository.findByEsArtistaTrueAndNombreUsuarioContainingIgnoreCase(nombreUsuario);

        return artistas.stream().map(usuario -> {
            PerfilArtista perfil = usuario.getPerfilArtista();

            List<BusquedaCancionDTO> canciones = perfil.getPerfilArtista_CancionList().stream()
                    .map(PerfilArtista_Cancion::getCancion) // obtenemos la canción
                    .filter(c -> c.getAlbum() == null)      // solo las que no están en álbum
                    .limit(10)
                    .map(c -> new BusquedaCancionDTO(
                            c.getIdCancion(),
                            c.getNombre(),
                            c.getDuracion(),
                            c.getArchivo(),
                            c.getFoto(),
                            usuario.getNombre(),
                            c.getFechaPublicacion(),
                            c.getAlbum().getNombre()
                    ))
                    .collect(Collectors.toList());

            return new BusquedaArtistaDTO(
                    perfil.getIdPerfilArtista(),
                    usuario.getNombre(),
                    usuario.getNombreUsuario(),
                    perfil.getDescripcion(),
                    perfil.getFoto(),
                    canciones
            );
        }).collect(Collectors.toList());
    }


}
