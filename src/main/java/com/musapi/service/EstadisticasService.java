/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.service;

import com.musapi.dto.ArtistaMasEscuchadoDTO;
import com.musapi.dto.CancionMasEscuchadaDTO;
import com.musapi.dto.EstadisticasContenidoSubidoDTO;
import com.musapi.dto.EstadisticasNumeroUsuariosDTO;
import com.musapi.dto.EstadisticasPersonalesDTO;
import com.musapi.model.Album;
import com.musapi.model.Cancion;
import com.musapi.model.Escucha;
import com.musapi.model.PerfilArtista;
import com.musapi.model.PerfilArtista_Cancion;
import com.musapi.model.Usuario;
import com.musapi.repository.ContenidoGuardadoRepository;
import com.musapi.repository.EscuchaRepository;
import com.musapi.repository.ListaDeReproduccion_CancionRepository;
import com.musapi.repository.PerfilArtistaRepository;
import com.musapi.repository.UsuarioRepository;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author axell
 */
@Service
public class EstadisticasService {
    
    @Autowired
    private PerfilArtistaRepository perfilArtistaRepository;

    @Autowired
    private EscuchaRepository escuchaRepository;

    @Autowired
    private ContenidoGuardadoRepository contenidoGuardadoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ListaDeReproduccion_CancionRepository lrcRepository;
    
    public EstadisticasContenidoSubidoDTO obtenerEstadisticas(Integer idPerfilArtista, String tipoContenido) {
        PerfilArtista artista = perfilArtistaRepository.findById(idPerfilArtista)
                .orElseThrow(() -> new NoSuchElementException("Artista no encontrado"));

        /*
        Set<Integer> cancionesIds = artista.getPerfilArtista_CancionList()
                .stream()
                .map(pac -> pac.getCancion().getIdCancion())
                .collect(Collectors.toSet());

        Set<Integer> oyentes = escuchaRepository.findOyentesByCancionIds(cancionesIds);
        int numeroOyentes = oyentes.size();*/
        int numeroOyentes = escuchaRepository.countUsuariosUnicosQueEscucharonArtista(idPerfilArtista);
        
        int numeroGuardados = 0;
        if (tipoContenido.equalsIgnoreCase("Cancion")) {
            /*Set<Integer> usuariosGuardaronContenido = contenidoGuardadoRepository
                    .findUsuariosByCancionIds(cancionesIds);

            Map<Integer, Set<Integer>> usuarioCancionesMap = new HashMap<>();
            lrcRepository.findByCancionIds(cancionesIds).forEach(rel -> {
                Integer idUsuario = rel.getListaDeReproduccion().getUsuario().getIdUsuario();
                Integer idCancion = rel.getCancion().getIdCancion();
                usuarioCancionesMap.computeIfAbsent(idUsuario, k -> new HashSet<>()).add(idCancion);
            });

            Set<Integer> usuariosFinales = new HashSet<>(usuariosGuardaronContenido);
            usuariosFinales.addAll(usuarioCancionesMap.keySet());
            numeroGuardados = usuariosFinales.size();*/
            numeroGuardados = escuchaRepository.countAparicionesEnListasDeReproduccion(idPerfilArtista);

        } else if (tipoContenido.equalsIgnoreCase("Album")) {
            Set<Integer> albumIds = artista.getAlbumes()
                    .stream()
                    .map(Album::getIdAlbum)
                    .collect(Collectors.toSet());

            numeroGuardados = contenidoGuardadoRepository.findUsuariosByAlbumIds(albumIds).size();
        } else {
            throw new IllegalArgumentException("Tipo de contenido no válido");
        }

        return new EstadisticasContenidoSubidoDTO(numeroOyentes, numeroGuardados);
    }
    
    public EstadisticasPersonalesDTO obtenerEstadisticasPersonales(Integer idUsuario, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Escucha> escuchas = escuchaRepository.findByUsuario_IdUsuarioAndFechaEscuchaBetween(idUsuario, fechaInicio, fechaFin);
        for (Escucha escucha : escuchas) {
            System.out.println("\t- Escucha encontrada con id: " + escucha.getIdEscucha()+" y valor: "+escucha.getTiempoEscucha());
        }
        long totalSegundos = escuchas.stream()
                .filter(e -> e.getTiempoEscucha() != null)
                .mapToLong(e -> e.getTiempoEscucha().toSecondOfDay())
                .sum();

        Map<Cancion, Long> conteoCanciones = escuchas.stream()
                .collect(Collectors.groupingBy(Escucha::getCancion, Collectors.counting()));

        List<String> topCanciones = conteoCanciones.entrySet().stream()
                .sorted(Map.Entry.<Cancion, Long>comparingByValue()
                        .reversed()
                        .thenComparing(entry -> entry.getKey().getNombre()))
                .limit(5)
                .map(entry -> {
                    Cancion cancion = entry.getKey();
                    String nombreCancion = cancion.getNombre();
                    String artistas = cancion.getPerfilArtista_CancionList()
                            .stream()
                            .map(pac -> pac.getPerfilArtista().getUsuario().getNombreUsuario())
                            .distinct()
                            .collect(Collectors.joining(", "));
                    return nombreCancion + " - " + artistas;
                })
                .collect(Collectors.toList());

        Map<String, Long> conteoArtistas = escuchas.stream()
                .flatMap(e -> e.getCancion().getPerfilArtista_CancionList().stream())
                .map(pac -> pac.getPerfilArtista().getUsuario().getNombreUsuario())
                .collect(Collectors.groupingBy(nombre -> nombre, Collectors.counting()));

        List<String> topArtistas = conteoArtistas.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return new EstadisticasPersonalesDTO(topCanciones, topArtistas, totalSegundos);
    }
    
    public EstadisticasNumeroUsuariosDTO obtenerConteoUsuariosYArtistas() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        Integer totalArtistas = (int)usuarios.stream().filter(Usuario::getEsArtista).count();
        Integer totalUsuarios = (int)usuarios.stream().filter(u -> !u.getEsArtista()).count();
        return new EstadisticasNumeroUsuariosDTO(totalUsuarios, totalArtistas);
    }

    public List<ArtistaMasEscuchadoDTO> obtenerTop10ArtistasMasEscuchados(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Escucha> escuchas = escuchaRepository.findByFechaEscuchaBetween(fechaInicio, fechaFin);

        Map<String, Long> artistaSegundosMap = new HashMap<>();

        for (Escucha escucha : escuchas) {
            if (escucha.getTiempoEscucha() == null) continue;
            long segundos = escucha.getTiempoEscucha().toSecondOfDay() ;

            for (PerfilArtista_Cancion pac : escucha.getCancion().getPerfilArtista_CancionList()) {
                String artista = pac.getPerfilArtista().getUsuario().getNombreUsuario();
                artistaSegundosMap.put(artista, artistaSegundosMap.getOrDefault(artista, 0L) + segundos);
            }
        }

        return artistaSegundosMap.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .map(entry -> new ArtistaMasEscuchadoDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public List<CancionMasEscuchadaDTO> obtenerTop10CancionesMasEscuchadas(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Escucha> escuchas = escuchaRepository.findByFechaEscuchaBetween(fechaInicio, fechaFin);

        Map<Cancion, Long> segundosPorCancion = new HashMap<>();

        for (Escucha escucha : escuchas) {
            if (escucha.getTiempoEscucha() == null) continue;
            long minutos = escucha.getTiempoEscucha().toSecondOfDay();
            Cancion c = escucha.getCancion();
            segundosPorCancion.put(c, segundosPorCancion.getOrDefault(c, 0L) + minutos);
        }

        return segundosPorCancion.entrySet().stream()
                .sorted(Map.Entry.<Cancion, Long>comparingByValue().reversed())
                .limit(10)
                .map(entry -> {
                    Cancion cancion = entry.getKey();
                    String artista = cancion.getPerfilArtista_CancionList().stream()
                            .map(pac -> pac.getPerfilArtista().getUsuario().getNombreUsuario())
                            .distinct()
                            .collect(Collectors.joining(", "));

                    return new CancionMasEscuchadaDTO(cancion.getNombre(), artista, entry.getValue());
                })
                .collect(Collectors.toList());
    }



}
