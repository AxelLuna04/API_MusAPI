package com.musapi.service;

import com.musapi.dto.BusquedaCancionDTO;
import com.musapi.model.*;
import com.musapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecomendacionService {

    @Autowired
    private EscuchaRepository escuchaRepository;
    @Autowired
    private CancionRepository cancionRepository;
    @Autowired
    private ContenidoGuardadoRepository contenidoGuardadoRepository;

    private static class CancionPuntuada {
        Cancion cancion;
        double scoreFinal;

        public CancionPuntuada(Cancion cancion, double scoreFinal) {
            this.cancion = cancion;
            this.scoreFinal = scoreFinal;
        }
    }

    public List<BusquedaCancionDTO> generarRecomendaciones(Integer idUsuario) {
        
        Map<Integer, Double> afinidadGeneros = new HashMap<>();
        Map<Integer, Double> afinidadArtistas = new HashMap<>();
        
        List<Escucha> historial = escuchaRepository.findByUsuario_IdUsuario(idUsuario);
        List<ContenidoGuardado> guardados = contenidoGuardadoRepository.findByUsuario_IdUsuario(idUsuario);
        
        if (!historial.isEmpty() || !guardados.isEmpty()) {
            analizarPreferencias(historial, guardados, afinidadGeneros, afinidadArtistas);
        }

        List<Integer> idsGenerosPreferidos = new ArrayList<>(afinidadGeneros.keySet());
        if(idsGenerosPreferidos.isEmpty()) idsGenerosPreferidos.add(-1);

        List<Integer> idsArtistasPreferidos = new ArrayList<>(afinidadArtistas.keySet());
        if(idsArtistasPreferidos.isEmpty()) idsArtistasPreferidos.add(-1);

        List<CancionStatDTO> candidatos = cancionRepository.findCandidatosParaRanking(
                idUsuario, 
                idsGenerosPreferidos, 
                idsArtistasPreferidos
        );

        if (candidatos.isEmpty()) {
             return convertirADTO(cancionRepository.findTopCancionesGlobalesSimple());
        }

        List<CancionPuntuada> ranking = new ArrayList<>();

        for (CancionStatDTO candidato : candidatos) {
            Cancion c = candidato.getCancion();
            double score = 0.0;

            for (PerfilArtista_Cancion pac : c.getPerfilArtista_CancionList()) {
                Integer idArtista = pac.getPerfilArtista().getIdPerfilArtista();
                if (afinidadArtistas.containsKey(idArtista)) {
                    score += afinidadArtistas.get(idArtista) * 5.0; 
                }
            }

            Integer idGenero = c.getCategoriaMusical().getIdCategoriaMusical();
            if (afinidadGeneros.containsKey(idGenero)) {
                score += afinidadGeneros.get(idGenero) * 2.0;
            }

            long totalEscuchas = candidato.getTotalEscuchas() != null ? candidato.getTotalEscuchas() : 0;
            score += (totalEscuchas / 100.0); 

            long totalGuardados = candidato.getTotalGuardados() != null ? candidato.getTotalGuardados() : 0;
            score += (totalGuardados / 10.0);

            ranking.add(new CancionPuntuada(c, score));
        }

        List<Cancion> topCanciones = ranking.stream()
                .sorted((p1, p2) -> Double.compare(p2.scoreFinal, p1.scoreFinal)) // Mayor a menor
                .limit(20) 
                .map(cp -> cp.cancion)
                .collect(Collectors.toList());

        return convertirADTO(topCanciones);
    }

    private void analizarPreferencias(List<Escucha> historial, List<ContenidoGuardado> guardados, 
                                      Map<Integer, Double> mapGeneros, Map<Integer, Double> mapArtistas) {
        
        for (Escucha e : historial) {
            double puntos = calcularPuntosPorTiempo(e); 
            sumarPuntos(e.getCancion(), puntos, mapGeneros, mapArtistas);
        }

        for (ContenidoGuardado g : guardados) {
            if (g.getCancion() != null) {
                sumarPuntos(g.getCancion(), 10.0, mapGeneros, mapArtistas);
            }
        }
    }

    private void sumarPuntos(Cancion cancion, double puntos, Map<Integer, Double> mapGeneros, Map<Integer, Double> mapArtistas) {
        if (cancion.getCategoriaMusical() != null) {
            Integer idGen = cancion.getCategoriaMusical().getIdCategoriaMusical();
            mapGeneros.put(idGen, mapGeneros.getOrDefault(idGen, 0.0) + puntos);
        }
        cancion.getPerfilArtista_CancionList().forEach(pac -> {
            Integer idArt = pac.getPerfilArtista().getIdPerfilArtista();
            mapArtistas.put(idArt, mapArtistas.getOrDefault(idArt, 0.0) + puntos);
        });
    }

    private double calcularPuntosPorTiempo(Escucha escucha) {
        if (escucha.getTiempoEscucha() == null || escucha.getCancion().getDuracion() == null) return 0.5;
        
        long segundosEscuchados = escucha.getTiempoEscucha().toSecondOfDay();
        long duracionTotal = escucha.getCancion().getDuracion().toSecondOfDay();
        
        if (duracionTotal == 0) return 0;

        double porcentaje = (double) segundosEscuchados / duracionTotal;
        
        if (porcentaje >= 0.90) return 3.0;
        if (porcentaje >= 0.50) return 1.5;
        return 0.5;
    }

    private List<BusquedaCancionDTO> convertirADTO(List<Cancion> canciones) {
        return canciones.stream().map(cancion -> {
            String nombreArtistas = cancion.getPerfilArtista_CancionList().isEmpty()
                    ? "Varios Artistas"
                    : cancion.getPerfilArtista_CancionList().stream()
                        .map(pac -> pac.getPerfilArtista().getUsuario().getNombreUsuario())
                        .collect(Collectors.joining(", "));
            
             return new BusquedaCancionDTO(
                    cancion.getIdCancion(),
                    cancion.getNombre(),
                    cancion.getDuracion().toString(),
                    cancion.getUrlArchivo(),
                    cancion.getUrlFoto() == null ? (cancion.getAlbum() != null ? cancion.getAlbum().getUrlFoto() : null) : cancion.getUrlFoto(),
                    nombreArtistas,
                    cancion.getFechaPublicacion() != null ? cancion.getFechaPublicacion().toString() : null,
                    cancion.getAlbum() == null ? null : cancion.getAlbum().getNombre(),
                    cancion.getCategoriaMusical().getNombre()
            );
        }).collect(Collectors.toList());
    }
}
