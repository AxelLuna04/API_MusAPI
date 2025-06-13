/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.service;

import com.musapi.dto.BusquedaArtistaDTO;
import com.musapi.dto.BusquedaCancionDTO;
import com.musapi.dto.EdicionPerfilDTO;
import com.musapi.dto.PerfilArtistaDTO;
import com.musapi.model.PerfilArtista;
import com.musapi.model.PerfilArtista_Cancion;
import com.musapi.model.Usuario;
import com.musapi.repository.PerfilArtistaRepository;
import com.musapi.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
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
    
    @Autowired
    private CorreoService correoService;
    
    @Transactional
    public boolean editarPerfil(Integer idUsuario, EdicionPerfilDTO edicionPerfil) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        if (edicionPerfil.getNombre() != null)
            usuario.setNombre(edicionPerfil.getNombre());

        if (edicionPerfil.getNombreUsuario() != null)
            usuario.setNombreUsuario(edicionPerfil.getNombreUsuario());

        if (edicionPerfil.getPais() != null)
            usuario.setPais(edicionPerfil.getPais());

        if (Boolean.TRUE.equals(usuario.getEsArtista())) {
            PerfilArtista perfil = usuario.getPerfilArtista();
            if (perfil != null) {
                if (edicionPerfil.getDescripcion() != null) {
                    perfil.setDescripcion(edicionPerfil.getDescripcion());
                }

                if (edicionPerfil.getFoto() != null && !edicionPerfil.getFoto().isEmpty()) {
                    if (perfil.getUrlFoto() != null) {
                        String rutaAntigua = System.getProperty("user.dir") + File.separator + perfil.getUrlFoto().replace("/", File.separator);
                        File archivoAntiguo = new File(rutaAntigua);

                        if (archivoAntiguo.exists()) {
                            archivoAntiguo.delete();
                        }
                    }
                    String nombreArchivo = "foto_" + usuario.getIdUsuario() + "_" + System.currentTimeMillis() + ".jpg";
                    String carpeta = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "fotos-artistas";
                    File directorio = new File(carpeta);
                    if (!directorio.exists()) {
                        directorio.mkdirs();
                    }
                    File destino = new File(directorio, nombreArchivo);


                    try {
                        edicionPerfil.getFoto().transferTo(destino);
                        perfil.setUrlFoto("/uploads/fotos-artistas/" + nombreArchivo);
                    } catch (IOException e) {
                        throw new IllegalArgumentException("Error al guardar la nueva imagen.");
                    }
                }
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
                    .map(PerfilArtista_Cancion::getCancion)
                    .filter(c -> c.getAlbum() == null)
                    .limit(10)
                    .map(c -> new BusquedaCancionDTO(
                            c.getIdCancion(),
                            c.getNombre(),
                            c.getDuracion().toString(),
                            c.getUrlArchivo(),
                            c.getUrlFoto(),
                            usuario.getNombre(),
                            c.getFechaPublicacion().toString(),
                            c.getAlbum().getNombre(),
                            c.getCategoriaMusical().getNombre()
                    ))
                    .collect(Collectors.toList());

            return new BusquedaArtistaDTO(
                    perfil.getIdPerfilArtista(),
                    usuario.getNombre(),
                    usuario.getNombreUsuario(),
                    perfil.getDescripcion(),
                    perfil.getUrlFoto(),
                    canciones
            );
        }).collect(Collectors.toList());
    }
    
    public BusquedaArtistaDTO obtenerArtistaPorId(Integer idArtista) {
        Usuario artista = usuarioRepository.findByIdUsuarioAndEsArtistaTrue(idArtista);
        PerfilArtista perfil = artista.getPerfilArtista();

        List<BusquedaCancionDTO> canciones = perfil.getPerfilArtista_CancionList().stream()
                .map(PerfilArtista_Cancion::getCancion)
                .filter(c -> c.getAlbum() == null)
                .limit(10)
                .map(c -> new BusquedaCancionDTO(
                        c.getIdCancion(),
                        c.getNombre(),
                        c.getDuracion().toString(),
                        c.getUrlArchivo(),
                        c.getUrlFoto(),
                        artista.getNombre(),
                        c.getFechaPublicacion().toString(),
                        c.getAlbum() != null ? c.getAlbum().getNombre() : null,
                        c.getCategoriaMusical().getNombre()
                ))
                .collect(Collectors.toList());

        return new BusquedaArtistaDTO(
                perfil.getIdPerfilArtista(),
                artista.getNombre(),
                artista.getNombreUsuario(),
                perfil.getDescripcion(),
                perfil.getUrlFoto(),
                canciones
        );
    }
    
    @Transactional
    public void crearPerfilArtista(PerfilArtistaDTO perfilArtistaDTO) throws Exception {
        Usuario usuario = usuarioRepository.findByIdUsuario(perfilArtistaDTO.getIdUsuario());

        if (usuario == null) {
            throw new Exception("Usuario no encontrado.");
        }

        PerfilArtista perfil = new PerfilArtista();
        perfil.setDescripcion(perfilArtistaDTO.getDescripcion());
        perfil.setUsuario(usuario);

        if (perfilArtistaDTO.getFoto() != null) {
            String carpeta = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "fotos-artistas";
            File directorio = new File(carpeta);

            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            String nombreArchivo = "foto_" + perfilArtistaDTO.getIdUsuario() + "_" + System.currentTimeMillis() + ".jpg";
            File destino = new File(directorio, nombreArchivo);

            try {
                perfilArtistaDTO.getFoto().transferTo(destino);
                perfil.setUrlFoto("/uploads/fotos-artistas/" + nombreArchivo);
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Error al guardar la imagen: " + e.getMessage());
            }

        }
          
        perfilArtistaRepository.save(perfil);
        usuario.setEsArtista(true);
        usuario.setPerfilArtista(perfil);
        usuarioRepository.save(usuario);
    }
    

    @Transactional
    public void eliminarUsuario(Integer idUsuario, String motivo) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        String correoDestino = usuario.getCorreo();
        String nombre = usuario.getNombreUsuario();

        String asunto = "Cuenta eliminada de Musapi";
        String cuerpo = "Hola " + nombre + ",\n\nTu cuenta ha sido eliminada por el siguiente motivo:\n\n"
                + motivo + "\n\nSi crees que esto fue un error, por favor contáctanos.\n\nSaludos,\nEquipo de Musapi";

        correoService.enviarCorreo(correoDestino, asunto, cuerpo);
        usuarioRepository.delete(usuario);
    }

}
