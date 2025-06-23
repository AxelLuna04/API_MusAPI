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
import com.musapi.model.Notificacion;
import com.musapi.model.PerfilArtista;
import com.musapi.model.PerfilArtista_Cancion;
import com.musapi.model.SolicitudColaboracion;
import com.musapi.repository.AlbumRepository;
import com.musapi.repository.CancionRepository;
import com.musapi.repository.CategoriaMusicalRepository;
import com.musapi.repository.ListaDeReproduccion_CancionRepository;
import com.musapi.repository.NotificacionRepository;
import com.musapi.repository.PerfilArtistaRepository;
import com.musapi.repository.PerfilArtista_CancionRepository;
import com.musapi.repository.SolicitudColaboracionRepository;
import jakarta.transaction.Transactional;
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
    private SolicitudColaboracionRepository solicitudColaboracionRepository;
    
    @Autowired
    private NotificacionRepository notificacionRepository;
    
    @Autowired
    private AlbumRepository albumRepository;
    
    @Autowired
    private NotificacionService notificacionService;
    
    @Autowired
    private ListaDeReproduccion_CancionRepository listaDeReproduccion_CancionRepository;
    
    public List<BusquedaCancionDTO> buscarCancionesPorNombre(String texto){
        List<Cancion> cancionesEncontradas = cancionRepository.findByEstadoAndNombreContainingIgnoreCase("publica", texto);
        
        return cancionesEncontradas.stream()
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
                            cancion.getUrlFoto() == null ? cancion.getAlbum().getUrlFoto() : cancion.getUrlFoto(),
                            nombreArtistas,
                            cancion.getFechaPublicacion().toString(),
                            cancion.getAlbum() == null ? null : cancion.getAlbum().getNombre(),
                            cancion.getCategoriaMusical().getNombre()
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
            throw new IllegalArgumentException("Formato de duración inválido.");
        }

        cancion.setNombre(cancionDTO.getNombre());
        cancion.setDuracion(duracion);

        if (cancionDTO.getArchivoCancion() != null && !cancionDTO.getArchivoCancion().isEmpty()) { 
            String carpeta = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "archivos-canciones";
            File directorio = new File(carpeta);

            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            String nombreArchivo = "cancion_" + cancionDTO.getIdPerfilArtistas().get(0) + "_" + cancionDTO.getNombre() + "_" + System.currentTimeMillis() + ".mp3";
            File destino = new File(directorio, nombreArchivo);

            try {
                cancionDTO.getArchivoCancion().transferTo(destino);
                cancion.setUrlArchivo("/uploads/archivos-canciones/" + nombreArchivo);
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Error al guardar la cancion: " + e.getMessage());
            }
        }

        if (cancionDTO.getUrlFoto() != null) { 
            String carpeta = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "fotos-canciones";
            File directorio = new File(carpeta);

            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            String nombreArchivo = "foto_cancion_" + cancionDTO.getIdPerfilArtistas().get(0) + "_" + cancionDTO.getNombre() + "_" + System.currentTimeMillis() + ".jpg";
            File destino = new File(directorio, nombreArchivo);

            try {
                cancionDTO.getUrlFoto().transferTo(destino);
                cancion.setUrlFoto("/uploads/fotos-canciones/" + nombreArchivo);
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Error al guardar la imagen: " + e.getMessage());
            }
        }

        CategoriaMusical categoriaMusical = categoriaMusicalRepository.findByIdCategoriaMusical(cancionDTO.getIdCategoriaMusical());
        if (categoriaMusical == null) return "Categoría musical no encontrada";
        cancion.setCategoriaMusical(categoriaMusical);

        Album album = null;
        if (cancionDTO.getIdAlbum() == null) return "Error al transferir el dato";
        if (cancionDTO.getIdAlbum() != 0) {
            album = albumRepository.findByIdAlbum(cancionDTO.getIdAlbum());
            if (album == null) return "Álbum no encontrado";
            cancion.setAlbum(album);
            cancion.setPosicionEnAlbum(album.getTotalCanciones()+1);
        }

        boolean esColaboracion = cancionDTO.getIdPerfilArtistas().size() > 1;
        if (esColaboracion || album != null) {
            cancion.setEstado("pendiente");
            cancion.setFechaPublicacion(null);
        } else {
            cancion.setEstado("publica");
            cancion.setFechaPublicacion(LocalDate.now());
            
            if (!esColaboracion && album == null) {
                PerfilArtista perfil = perfilArtistaRepository.findByIdPerfilArtista(cancionDTO.getIdPerfilArtistas().get(0));
                String mensaje = "El artista " + perfil.getUsuario().getNombreUsuario() + " ha publicado una nueva canción: " + cancion.getNombre();
                notificacionService.notificarSeguidores(perfil, mensaje);
            }

        }

        cancionRepository.save(cancion);

        List<Integer> idsArtistas = cancionDTO.getIdPerfilArtistas();
        for (int i = 0; i < idsArtistas.size(); i++) {
            Integer idPerfilArtista = idsArtistas.get(i);
            PerfilArtista perfil = perfilArtistaRepository.findByIdPerfilArtista(idPerfilArtista);
            if (perfil == null) return "Artista no encontrado";

            PerfilArtista_Cancion relacion = new PerfilArtista_Cancion();
            relacion.setCancion(cancion);
            relacion.setPerfilArtista(perfil);
            perfilArtista_CancionRepository.save(relacion);

            if (i != 0) {
                Notificacion notificacion = new Notificacion();
                notificacion.setMensaje("Has sido invitado a colaborar en la canción: " + cancion.getNombre());
                notificacion.setFechaEnvio(LocalDate.now());
                notificacion.setFueLeida(false);
                notificacion.setUsuario(perfil.getUsuario());

                notificacionRepository.save(notificacion);

                SolicitudColaboracion solicitud = new SolicitudColaboracion();
                solicitud.setNotificacion(notificacion);
                solicitud.setCancion(cancion);
                solicitud.setEstado("pendiente");

                solicitudColaboracionRepository.save(solicitud);
            }
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
        
        if (cancionDTO.getUrlFoto() != null && !cancionDTO.getUrlFoto().isEmpty()) {
            if (cancion.getUrlFoto() != null) {
                String rutaAntigua = System.getProperty("user.dir") + File.separator + cancion.getUrlFoto().replace("/", File.separator);
                File archivoAntiguo = new File(rutaAntigua);

                if (archivoAntiguo.exists()) {
                    archivoAntiguo.delete();
                }
            }
            String nombreArchivo = "foto_cancion_" + cancionDTO.getIdPerfilArtistas().get(0) + cancion.getNombre() + "_" + System.currentTimeMillis() + ".jpg";
            String carpeta = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "fotos-canciones";
            File directorio = new File(carpeta);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }
            File destino = new File(directorio, nombreArchivo);


            try {
                cancionDTO.getUrlFoto().transferTo(destino);
                cancion.setUrlFoto("/uploads/fotos-canciones/" + nombreArchivo);
            } catch (IOException e) {
                throw new IllegalArgumentException("Error al guardar la nueva imagen.");
            }
        }
        
        if (cancionDTO.getArchivoCancion() != null && !cancionDTO.getArchivoCancion().isEmpty()) {
            if (cancion.getUrlArchivo() != null) {
                String rutaAntigua = System.getProperty("user.dir") + File.separator + cancion.getUrlArchivo().replace("/", File.separator);
                File archivoAntiguo = new File(rutaAntigua);

                if (archivoAntiguo.exists()) {
                    archivoAntiguo.delete();
                }
            }
            String nombreArchivo = "cancion_" + cancionDTO.getIdPerfilArtistas().get(0) + cancion.getNombre() + "_" + System.currentTimeMillis() + ".mp3";
            String carpeta = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "archivos-canciones";
            File directorio = new File(carpeta);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }
            File destino = new File(directorio, nombreArchivo);

            try {
                cancionDTO.getArchivoCancion().transferTo(destino);
                cancion.setUrlArchivo("/uploads/archivos-canciones/" + nombreArchivo);
            } catch (IOException e) {
                throw new IllegalArgumentException("Error al guardar el nuevo archivo.");
            }
        }
        
        cancionRepository.save(cancion);
        return true;
        
    }
    
    public List<BusquedaCancionDTO> obtenerCancionesPorIdAlbum(Integer idAlbum) {
        Album album = albumRepository.findByIdAlbum(idAlbum);

        if (album == null) {
            throw new IllegalArgumentException("Álbum no encontrado.");
        }

        List<Cancion> canciones = cancionRepository.findByAlbum(album);

        return canciones.stream().map(cancion -> {
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
                    album.getUrlFoto(),
                    nombreArtistas,
                    cancion.getFechaPublicacion() != null ? cancion.getFechaPublicacion().toString() : null,
                    cancion.getAlbum().getNombre(),
                    cancion.getCategoriaMusical().getNombre()
            );
        }).collect(Collectors.toList());
    }
    
    public List<BusquedaCancionDTO> obtenerSencillosPorIdArtista(Integer idArtista) {
        PerfilArtista artista = perfilArtistaRepository.findByIdPerfilArtista(idArtista);
        if (artista == null){
            throw new IllegalArgumentException("Artista no encontrado.");
        }
        List<Cancion> canciones = cancionRepository.findByEstadoAndArtistaId("publica", idArtista);

        return canciones.stream().map(cancion -> {
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
                    cancion.getFechaPublicacion() != null ? cancion.getFechaPublicacion().toString() : null,
                    cancion.getAlbum() == null ? null : cancion.getAlbum().getNombre(),
                    cancion.getCategoriaMusical().getNombre()
            );
        }).collect(Collectors.toList());
    }
    
    public String eliminarCancion(Integer idCancion) {
        Cancion cancion = cancionRepository.findById(idCancion)
            .orElseThrow(() -> new IllegalArgumentException("Canción no encontrada."));

        if (cancion.getUrlArchivo() != null) {
            File archivo = new File(System.getProperty("user.dir") + File.separator + cancion.getUrlArchivo().replace("/", File.separator));
            if (archivo.exists()) archivo.delete();
        }

        if (cancion.getUrlFoto() != null) {
            File imagen = new File(System.getProperty("user.dir") + File.separator + cancion.getUrlFoto().replace("/", File.separator));
            if (imagen.exists()) imagen.delete();
        }

        perfilArtista_CancionRepository.deleteByCancion(cancion);
        listaDeReproduccion_CancionRepository.deleteByCancion(cancion);

        cancionRepository.delete(cancion);

        return "Canción eliminada correctamente.";
    }




}
