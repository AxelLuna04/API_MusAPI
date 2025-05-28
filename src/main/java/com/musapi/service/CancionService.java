/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.service;

import com.musapi.dto.BusquedaCancionDTO;
import com.musapi.dto.CancionDTO;
import com.musapi.model.Album;
import com.musapi.model.Cancion;
import com.musapi.model.CategoriaMusical;
import com.musapi.model.PerfilArtista;
import com.musapi.model.PerfilArtista_Cancion;
import com.musapi.repository.AlbumRepository;
import com.musapi.repository.CancionRepository;
import com.musapi.repository.CategoriaMusicalRepository;
import com.musapi.repository.PerfilArtistaRepository;
import com.musapi.repository.PerfilArtista_CancionRepository;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
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
public class CancionService {
    
    @Autowired
    private CancionRepository cancionRepository;
    
    @Autowired
    private PerfilArtistaRepository perfilArtistaRepository;
    
    @Autowired
    private PerfilArtista_CancionRepository perfilArtista_CancionRepository;
    
    @Autowired
    private CategoriaMusicalRepository categoriaMusicalRepository;
    
    @Autowired
    private AlbumRepository albumRepository;
    
    public List<BusquedaCancionDTO> buscarCancionesPorNombre(String texto){
        List<Cancion> cancionesEncontradas = cancionRepository.findByNombreContainingIgnoreCase(texto);
        
        return cancionesEncontradas.stream()
                .map(cancion -> {
                    String nombreArtista = cancion.getPerfilArtista_CancionList().isEmpty()
                            ? null
                            : cancion.getPerfilArtista_CancionList().get(0).getPerfilArtista().getUsuario().getNombreUsuario();
                    
                    return new BusquedaCancionDTO(
                            cancion.getIdCancion(),
                            cancion.getNombre(),
                            cancion.getDuracion(),
                            cancion.getUrlArchivo(),
                            cancion.getUrlArchivo(),
                            nombreArtista,
                            cancion.getFechaPublicacion(),
                            cancion.getAlbum().getNombre()
                    );
                })
                .collect(Collectors.toList());
    }
    
    public String SubirCancion(CancionDTO cancionDTO){
        Cancion cancion = new Cancion();
        LocalTime duracion;
        try {
            duracion = LocalTime.parse(cancionDTO.getDuracionStr());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido.");
        }
        cancion.setNombre(cancionDTO.getNombre());
        cancion.setDuracion(duracion);
        
        if (cancionDTO.getArchivoCancion() != null && !cancionDTO.getArchivoCancion().isEmpty()) {
            String nombreArchivo = "archivo_" + cancionDTO.getIdPerfilArtistas() + cancionDTO.getNombre() + "_" + System.currentTimeMillis() + ".jpg";
            String rutaDestino = "uploads/archivos-canciones/" + nombreArchivo;
            java.io.File destino = new java.io.File(rutaDestino);
            
            destino.getParentFile().mkdirs();
            
            try {
                cancionDTO.getArchivoCancion().transferTo(destino);
                cancion.setUrlFoto("/" + rutaDestino);
            } catch (IOException e) {
                throw new IllegalArgumentException("Error al guardar el archivo.");
            }
        }
        
        if (cancionDTO.getFoto() != null) {
            String nombreArchivo = "foto_" + cancionDTO.getIdPerfilArtistas() + cancionDTO.getNombre() + "_" + System.currentTimeMillis() + ".jpg";
            String rutaDestino = "uploads/fotos-canciones/" + nombreArchivo;
            java.io.File destino = new java.io.File(rutaDestino);
            
            destino.getParentFile().mkdirs();
            
            try {
                cancionDTO.getFoto().transferTo(destino);
                cancion.setUrlFoto("/" + rutaDestino);
            } catch (IOException e) {
                throw new IllegalArgumentException("Error al guardar la imagen.");
            }
        }
        
        CategoriaMusical categoriaMusical = categoriaMusicalRepository.findByIdCategoriaMusical(cancionDTO.getIdCategoriaMusical());
        
        if(categoriaMusical == null)
            return "Categoría musical no encontrada";
        else
            cancion.setCategoriaMusical(categoriaMusical);
        
        Album album;
        
        if(cancionDTO.getIdAlbum() == null)
            return "Error al transferir el dato";
        
        if(cancionDTO.getIdAlbum() != 0){
            album = albumRepository.findByIdAlbum(cancionDTO.getIdAlbum());
            
            if(album != null){
                cancion.setAlbum(album);
                cancion.setPosicionEnAlbum(cancionDTO.getPosicionEnAlbum());
                cancion.setEstado("pendiente");
                cancion.setFechaPublicacion(album.getFechaPublicacion());
            }else{
                return "Álbum no encontrado";
            }
        }else{
            cancion.setEstado("publica");
            cancion.setFechaPublicacion(LocalDate.now());
        }
        
        if(cancionDTO.getIdPerfilArtistas().size() > 1){
            cancion.setEstado("pendiente");
            cancion.setFechaPublicacion(null);
        }
        
        cancionRepository.save(cancion);
        
        for(Integer idPerfilArtista : cancionDTO.getIdPerfilArtistas()){
            PerfilArtista perfilArtista = perfilArtistaRepository.findByIdPerfilArtista(idPerfilArtista);
            
            if(perfilArtista != null){
                PerfilArtista_Cancion perfilArtista_Cancion = new PerfilArtista_Cancion();
                perfilArtista_Cancion.setCancion(cancion);
                perfilArtista_Cancion.setPerfilArtista(perfilArtista);
                
                perfilArtista_CancionRepository.save(perfilArtista_Cancion);
            }else
                return "Artista no encontrado";
        }
        return "Cancion registrada exitosamente.";
    }

    public boolean editarCancion(Integer idCancion, CancionDTO cancionDTO){
        Cancion cancion = cancionRepository.findById(idCancion)
            .orElseThrow(() -> new IllegalArgumentException("Cancion no encontrada."));
        
        if(cancionDTO.getIdAlbum() != null){
            Album album = albumRepository.findByIdAlbum(cancionDTO.getIdAlbum());
            cancion.setAlbum(album);
            if(cancionDTO.getPosicionEnAlbum() != null)
                cancion.setPosicionEnAlbum(cancionDTO.getPosicionEnAlbum());
        }

        if(cancionDTO.getNombre() != null)
            cancion.setNombre(cancionDTO.getNombre());
        
        if(cancionDTO.getIdCategoriaMusical() != null){
            CategoriaMusical categoriaMusical = categoriaMusicalRepository.findByIdCategoriaMusical(cancionDTO.getIdCategoriaMusical());
            cancion.setCategoriaMusical(categoriaMusical);
        }
        
        if (cancionDTO.getFoto() != null && !cancionDTO.getFoto().isEmpty()) {
            if (cancion.getUrlFoto() != null) {
                String rutaAntigua = cancion.getUrlFoto().replaceFirst("/", "");
                File archivoAntiguo = new File(rutaAntigua);
                if (archivoAntiguo.exists()) {
                    archivoAntiguo.delete();
                }
            }
            String nombreArchivo = "foto_" + cancionDTO.getIdPerfilArtistas() + cancion.getNombre() + "_" + System.currentTimeMillis() + ".jpg";
            String rutaDestino = "uploads/fotos-canciones/" + nombreArchivo;
            File destino = new File(rutaDestino);

            destino.getParentFile().mkdirs();

            try {
                cancionDTO.getFoto().transferTo(destino);
                cancion.setUrlFoto("/" + rutaDestino);
            } catch (IOException e) {
                throw new IllegalArgumentException("Error al guardar la nueva imagen.");
            }
        }
        
        if (cancionDTO.getArchivoCancion() != null && !cancionDTO.getArchivoCancion().isEmpty()) {
            if (cancion.getUrlArchivo() != null) {
                String rutaAntigua = cancion.getUrlArchivo().replaceFirst("/", "");
                File archivoAntiguo = new File(rutaAntigua);
                if (archivoAntiguo.exists()) {
                    archivoAntiguo.delete();
                }
            }
            String nombreArchivo = "archivo_" + cancionDTO.getIdPerfilArtistas() + cancion.getNombre() + "_" + System.currentTimeMillis() + ".jpg";
            String rutaDestino = "uploads/archivos-canciones/" + nombreArchivo;
            File destino = new File(rutaDestino);

            destino.getParentFile().mkdirs();

            try {
                cancionDTO.getArchivoCancion().transferTo(destino);
                cancion.setUrlArchivo("/" + rutaDestino);
            } catch (IOException e) {
                throw new IllegalArgumentException("Error al guardar el nuevo archivo.");
            }
        }
        
        cancionRepository.save(cancion);
        return true;
        
    }
}
