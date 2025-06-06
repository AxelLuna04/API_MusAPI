/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.service;

import com.musapi.dto.BusquedaCancionDTO;
import com.musapi.dto.CreacionListaDeReproduccionDTO;
import com.musapi.dto.ListaDeReproduccionDTO;
import com.musapi.dto.ListaDeReproduccion_CancionDTO;
import com.musapi.model.Cancion;
import com.musapi.model.ListaDeReproduccion;
import com.musapi.model.ListaDeReproduccion_Cancion;
import com.musapi.model.Usuario;
import com.musapi.repository.CancionRepository;
import com.musapi.repository.ListaDeReproduccionRepository;
import com.musapi.repository.UsuarioRepository;
import java.io.File;
import java.io.IOException;
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
public class ListaDeReproduccionService {
    
    @Autowired
    private ListaDeReproduccionRepository listaDeReproduccionRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private CancionRepository cancionRepository;
    
    public String crearListaDeReproduccion(CreacionListaDeReproduccionDTO creacionListaDeReproduccionDTO){
        Usuario usuario = usuarioRepository.findByIdUsuario(creacionListaDeReproduccionDTO.getIdUsuario());
            
        if (usuario == null) {
            return "Usuario no encontrado.";
        }
            
        ListaDeReproduccion listaDeReproduccion = new ListaDeReproduccion();
        listaDeReproduccion.setNombre(creacionListaDeReproduccionDTO.getNombre());
        listaDeReproduccion.setDescripcion(creacionListaDeReproduccionDTO.getDescripcion());
        listaDeReproduccion.setUsuario(usuario);
        
        if (creacionListaDeReproduccionDTO.getFoto() != null) {
            String carpeta = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "fotos-listasDeReproduccion";
            File directorio = new File(carpeta);

            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            String nombreArchivo = "foto_" + creacionListaDeReproduccionDTO.getIdUsuario() + "_" + creacionListaDeReproduccionDTO.getNombre() + "_" + System.currentTimeMillis() + ".jpg";
            File destino = new File(directorio, nombreArchivo);

            try {
                creacionListaDeReproduccionDTO.getFoto().transferTo(destino);
                listaDeReproduccion.setUrlFoto("/uploads/fotos-listasDeReproduccion/" + nombreArchivo);
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Error al guardar la imagen: " + e.getMessage());
            }
        }
            
        listaDeReproduccionRepository.save(listaDeReproduccion);
        return "Lista creada con éxito";
    }
    
    public String agregarCancionALista(ListaDeReproduccion_CancionDTO dto) {
        ListaDeReproduccion lista = listaDeReproduccionRepository.findByIdListaDeReproduccion(dto.getIdListaDeReproduccion());
        if (lista == null) {
            return "La lista de reproducción no existe";
        }

        if (!lista.getUsuario().getIdUsuario().equals(dto.getIdUsuario())) {
            return "La lista no pertenece al usuario";
        }

        Cancion cancion = cancionRepository.findByIdCancion(dto.getIdCancion());
        if (cancion == null) {
            return "La canción no existe";
        }

        boolean yaExiste = lista.getListaDeReproduccion_CancionList().stream()
            .anyMatch(rel -> rel.getCancion().getIdCancion().equals(dto.getIdCancion()));

        if (yaExiste) {
            return "La canción ya está en la lista";
        }

        ListaDeReproduccion_Cancion relacion = new ListaDeReproduccion_Cancion();
        relacion.setCancion(cancion);
        relacion.setListaDeReproduccion(lista);
        relacion.setPosicionCancion(lista.getListaDeReproduccion_CancionList().size() + 1);

        lista.getListaDeReproduccion_CancionList().add(relacion);
        listaDeReproduccionRepository.save(lista);

        return "Canción agregada correctamente";
    }
    
    public List<ListaDeReproduccionDTO> obtenerListaDeReproduccionPorIdUsuario(Integer idUsuario){
        Usuario usuario = usuarioRepository.findByIdUsuario(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        List<ListaDeReproduccion> listas = listaDeReproduccionRepository.findByUsuario(usuario);

        return listas.stream().map(lista -> {
            ListaDeReproduccionDTO dto = new ListaDeReproduccionDTO();
            dto.setIdListaDeReproduccion(lista.getIdListaDeReproduccion());
            dto.setNombre(lista.getNombre());
            dto.setUrlFoto(lista.getUrlFoto());
            return dto;
        }).collect(Collectors.toList());
    }
    
    public List<BusquedaCancionDTO> obtenerCancionesPorIdListaDeReproduccion(Integer idListaDeReproduccion) {
    ListaDeReproduccion lista = listaDeReproduccionRepository.findByIdListaDeReproduccion(idListaDeReproduccion);

    if (lista == null) {
        throw new IllegalArgumentException("La lista de reproducción no existe.");
    }

    return lista.getListaDeReproduccion_CancionList().stream()
        .sorted(Comparator.comparingInt(ListaDeReproduccion_Cancion::getPosicionCancion))
        .map(relacion -> {
            Cancion cancion = relacion.getCancion();

            String nombreArtistas = cancion.getPerfilArtista_CancionList().isEmpty()
                ? null
                : cancion.getPerfilArtista_CancionList().stream()
                    .map(pac -> pac.getPerfilArtista().getUsuario().getNombreUsuario())
                    .collect(Collectors.joining(", "));

            BusquedaCancionDTO dto = new BusquedaCancionDTO();
            dto.setNombre(cancion.getNombre());
            dto.setDuracion(cancion.getDuracion().toString());
            dto.setUrlArchivo(cancion.getUrlArchivo());
            dto.setUrlFoto(cancion.getUrlFoto());
            dto.setNombreArtista(nombreArtistas);
            dto.setFechaPublicacion(cancion.getFechaPublicacion() != null ? cancion.getFechaPublicacion().toString() : null);
            dto.setNombreAlbum(cancion.getAlbum().getNombre());
            dto.setCategoriaMusical(cancion.getCategoriaMusical().getNombre());
            return dto;
        })
        .collect(Collectors.toList());
}



}
