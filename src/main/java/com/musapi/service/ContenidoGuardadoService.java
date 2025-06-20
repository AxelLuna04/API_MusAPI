/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.service;

import com.musapi.dto.BusquedaAlbumDTO;
import com.musapi.dto.BusquedaArtistaDTO;
import com.musapi.dto.BusquedaCancionDTO;
import com.musapi.dto.ContenidoGuardadoDTO;
import com.musapi.dto.ListaDeReproduccionDTO;
import com.musapi.model.Album;
import com.musapi.model.Cancion;
import com.musapi.model.ContenidoGuardado;
import com.musapi.model.ListaDeReproduccion;
import com.musapi.model.ListaDeReproduccion_Cancion;
import com.musapi.model.PerfilArtista;
import com.musapi.model.Usuario;
import com.musapi.repository.AlbumRepository;
import com.musapi.repository.CancionRepository;
import com.musapi.repository.ContenidoGuardadoRepository;
import com.musapi.repository.ListaDeReproduccionRepository;
import com.musapi.repository.PerfilArtistaRepository;
import com.musapi.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author axell
 */
@Service
public class ContenidoGuardadoService {
    @Autowired
    private ContenidoGuardadoRepository contenidoGuardadoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ListaDeReproduccionRepository listaRepository;
    
    @Autowired
    private CancionRepository cancionRepository;
    
    @Autowired
    private AlbumRepository albumRepository;
    
    @Autowired
    private PerfilArtistaRepository perfilArtistaRepository;
    
    public String guardarContenido(ContenidoGuardadoDTO dto){
        
        String mensajeCR = "Contenido repetido.";
        String mensajeCNE = "Contenido no encontrado";
        Usuario usuario = usuarioRepository.findByIdUsuario(dto.getIdUsuario());
        if (usuario == null) return "Usuario no encontrado";
        
        ContenidoGuardado contenido = new ContenidoGuardado();
        contenido.setUsuario(usuario);
        
        switch (dto.getTipoDeContenido()) {
            
            case ALBUM -> {
                Album album = albumRepository.findByIdAlbum(dto.getIdContenidoGuardado());
                if(album == null) return mensajeCNE;
                
                if(contenidoGuardadoRepository.findByUsuarioAndAlbum(usuario, album) != null) return mensajeCR;
                
                contenido.setAlbum(album);
            }
            case CANCION -> {
                Cancion cancion = cancionRepository.findByIdCancion(dto.getIdContenidoGuardado());
                if(cancion == null) return mensajeCNE;
                 
                if(contenidoGuardadoRepository.findByUsuarioAndCancion(usuario, cancion) != null) return mensajeCR;
                
                contenido.setCancion(cancion);
            }
                
            case LISTA -> {
                ListaDeReproduccion lista = listaRepository.findByIdListaDeReproduccion(dto.getIdContenidoGuardado());
                if(lista == null) return mensajeCNE;
                
                if(contenidoGuardadoRepository.findByUsuarioAndListaDeReproduccion(usuario, lista) != null) return mensajeCR;
                 
                contenido.setListaDeReproduccion(lista);
            }
                
            case ARTISTA -> {
                PerfilArtista artista = perfilArtistaRepository.findByIdPerfilArtista(dto.getIdContenidoGuardado());
                if(artista == null) return mensajeCNE;
                  
                if(contenidoGuardadoRepository.findByUsuarioAndPerfilArtista(usuario, artista) != null) return mensajeCR;
                
                contenido.setPerfilArtista(artista);
            }
            default -> {
                return "Tipo de contenido no disponible";
            }
        }
        System.out.println("Guardando contenido: tipo=" + dto.getTipoDeContenido() + ", id=" + dto.getIdContenidoGuardado());
        contenidoGuardadoRepository.save(contenido);
        return "Contenido guardado exitosamente";
    }
    
    public List<BusquedaCancionDTO> obtenerCancionesGuardadasPorUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findByIdUsuario(idUsuario);
        if (usuario == null) return Collections.emptyList();

        List<ContenidoGuardado> guardados = contenidoGuardadoRepository.findByUsuarioAndCancionIsNotNull(usuario);
        List<BusquedaCancionDTO> cancionesDTO = new ArrayList<>();

        for (ContenidoGuardado contenido : guardados) {
            Cancion cancion = contenido.getCancion();
            BusquedaCancionDTO dto = new BusquedaCancionDTO();
            dto.setIdCancion(cancion.getIdCancion());
            dto.setNombre(cancion.getNombre());
            dto.setDuracion(cancion.getDuracion().toString());
            dto.setUrlArchivo(cancion.getUrlArchivo());
            dto.setUrlFoto(cancion.getUrlFoto());

            String nombreArtistas = cancion.getPerfilArtista_CancionList().isEmpty()
                ? "Desconocido"
                : cancion.getPerfilArtista_CancionList().stream()
                    .map(pac -> pac.getPerfilArtista().getUsuario().getNombreUsuario())
                    .collect(Collectors.joining(", "));

            dto.setNombreArtista(nombreArtistas);
            dto.setFechaPublicacion(cancion.getFechaPublicacion() != null ? cancion.getFechaPublicacion().toString() : null);
            dto.setNombreAlbum(cancion.getAlbum().getNombre());
            dto.setCategoriaMusical(cancion.getCategoriaMusical().getNombre());

            cancionesDTO.add(dto);
        }

        return cancionesDTO;
}


    public List<ListaDeReproduccionDTO> obtenerListasGuardadasPorUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findByIdUsuario(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        List<ContenidoGuardado> guardados = contenidoGuardadoRepository.findByUsuarioAndListaDeReproduccionIsNotNull(usuario);

        return guardados.stream().map(contenido -> {
            ListaDeReproduccion lista = contenido.getListaDeReproduccion();

            ListaDeReproduccionDTO dto = new ListaDeReproduccionDTO();
            dto.setIdListaDeReproduccion(lista.getIdListaDeReproduccion());
            dto.setNombre(lista.getNombre());
            dto.setUrlFoto(lista.getUrlFoto());
            dto.setDescripcion(lista.getDescripcion());

            List<BusquedaCancionDTO> canciones = lista.getListaDeReproduccion_CancionList().stream()
                .sorted(Comparator.comparingInt(ListaDeReproduccion_Cancion::getPosicionCancion))
                .map(relacion -> {
                    Cancion cancion = relacion.getCancion();

                    String nombreArtistas = cancion.getPerfilArtista_CancionList().isEmpty()
                        ? null
                        : cancion.getPerfilArtista_CancionList().stream()
                            .map(pac -> pac.getPerfilArtista().getUsuario().getNombreUsuario())
                            .collect(Collectors.joining(", "));

                    BusquedaCancionDTO cancionDTO = new BusquedaCancionDTO();
                    cancionDTO.setIdCancion(cancion.getIdCancion());
                    cancionDTO.setNombre(cancion.getNombre());
                    cancionDTO.setDuracion(cancion.getDuracion().toString());
                    cancionDTO.setUrlArchivo(cancion.getUrlArchivo());
                    cancionDTO.setUrlFoto(cancion.getUrlFoto());
                    cancionDTO.setNombreArtista(nombreArtistas);
                    cancionDTO.setFechaPublicacion(
                        cancion.getFechaPublicacion() != null ? cancion.getFechaPublicacion().toString() : null
                    );
                    cancionDTO.setNombreAlbum(cancion.getAlbum() != null ? cancion.getAlbum().getNombre() : null);
                    cancionDTO.setCategoriaMusical(cancion.getCategoriaMusical().getNombre());

                    return cancionDTO;
                })
                .collect(Collectors.toList());

            dto.setCanciones(canciones);

            return dto;
        }).collect(Collectors.toList());
    }


    public List<BusquedaAlbumDTO> obtenerAlbumesGuardadosPorUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findByIdUsuario(idUsuario);
        if (usuario == null) return Collections.emptyList();

        List<ContenidoGuardado> guardados = contenidoGuardadoRepository.findByUsuarioAndAlbumIsNotNull(usuario);
        List<BusquedaAlbumDTO> albumesDTO = new ArrayList<>();

        for (ContenidoGuardado contenido : guardados) {
            Album album = contenido.getAlbum();
            BusquedaAlbumDTO dto = new BusquedaAlbumDTO();
            List<BusquedaCancionDTO> cancionesDTO = new ArrayList<>();

            dto.setIdAlbum(album.getIdAlbum());
            dto.setNombreAlbum(album.getNombre());
            dto.setNombreArtista(album.getPerfilArtista().getUsuario().getNombreUsuario());
            dto.setFechaPublicacion(album.getFechaPublicacion().toString());
            dto.setUrlFoto(album.getUrlFoto());

            for (Cancion cancion : album.getCanciones()) {
                BusquedaCancionDTO cancionDTO = new BusquedaCancionDTO();
                cancionDTO.setIdCancion(cancion.getIdCancion());
                cancionDTO.setNombre(cancion.getNombre());
                cancionDTO.setNombreAlbum(cancion.getAlbum().getNombre());
                cancionDTO.setDuracion(cancion.getDuracion().toString());
                cancionDTO.setCategoriaMusical(cancion.getCategoriaMusical().getNombre());
                cancionDTO.setFechaPublicacion(cancion.getFechaPublicacion().toString());
                cancionDTO.setUrlArchivo(cancion.getUrlArchivo());
                cancionDTO.setUrlFoto(cancion.getUrlFoto());

                String nombreArtistas = cancion.getPerfilArtista_CancionList().isEmpty()
                    ? "Desconocido"
                    : cancion.getPerfilArtista_CancionList().stream()
                        .map(pac -> pac.getPerfilArtista().getUsuario().getNombreUsuario())
                        .collect(Collectors.joining(", "));

                cancionDTO.setNombreArtista(nombreArtistas);
                cancionesDTO.add(cancionDTO);
            }

            dto.setCanciones(cancionesDTO);
            albumesDTO.add(dto);
        }

        return albumesDTO;
    }


    public List<BusquedaArtistaDTO> obtenerArtistasGuardadosPorUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findByIdUsuario(idUsuario);
        if (usuario == null) return Collections.emptyList();

        List<ContenidoGuardado> guardados = contenidoGuardadoRepository.findByUsuarioAndPerfilArtistaIsNotNull(usuario);
        List<BusquedaArtistaDTO> artistasDTO = new ArrayList<>();

        for (ContenidoGuardado contenido : guardados) {
            PerfilArtista artista = contenido.getPerfilArtista();
            BusquedaArtistaDTO dto = new BusquedaArtistaDTO();
            dto.setIdArtista(artista.getIdPerfilArtista());
            dto.setNombre(artista.getUsuario().getNombre());
            dto.setNombreUsuario(artista.getUsuario().getNombreUsuario());
            dto.setDescripcion(artista.getDescripcion());
            dto.setUrlFoto(artista.getUrlFoto());
            dto.setCanciones(null);
            artistasDTO.add(dto);
        }

        return artistasDTO;
    }

}
