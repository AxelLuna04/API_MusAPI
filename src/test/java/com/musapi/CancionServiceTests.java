/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi;

import com.musapi.dto.BusquedaCancionDTO;
import com.musapi.model.Album;
import com.musapi.model.Cancion;
import com.musapi.model.CategoriaMusical;
import com.musapi.model.PerfilArtista;
import com.musapi.model.PerfilArtista_Cancion;
import com.musapi.model.Usuario;
import com.musapi.repository.AlbumRepository;
import com.musapi.repository.CancionRepository;
import com.musapi.repository.ListaDeReproduccion_CancionRepository;
import com.musapi.repository.PerfilArtistaRepository;
import com.musapi.repository.PerfilArtista_CancionRepository;
import com.musapi.service.CancionService;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class CancionServiceTests {

    @InjectMocks
    private CancionService cancionService;

    @Mock
    private CancionRepository cancionRepository;

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private PerfilArtistaRepository perfilArtistaRepository;

    @Mock
    private ListaDeReproduccion_CancionRepository listaDeReproduccion_CancionRepository;

    @Mock
    private PerfilArtista_CancionRepository perfilArtista_CancionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void buscarCancionesPorNombre_deberiaRetornarListaDeDTOsCorrectamente() {
        Cancion cancion1 = mock(Cancion.class);
        Album album = mock(Album.class);
        CategoriaMusical categoria = mock(CategoriaMusical.class);
        PerfilArtista artista = mock(PerfilArtista.class);
        Usuario usuario = mock(Usuario.class);
        PerfilArtista_Cancion pac = mock(PerfilArtista_Cancion.class);

        when(cancion1.getIdCancion()).thenReturn(1);
        when(cancion1.getNombre()).thenReturn("Mi Canción");
        when(cancion1.getDuracion()).thenReturn(LocalTime.of(0, 3, 30));
        when(cancion1.getUrlArchivo()).thenReturn("http://archivo.mp3");
        when(cancion1.getUrlFoto()).thenReturn(null);
        when(cancion1.getFechaPublicacion()).thenReturn(LocalDate.of(2023, 6, 20));
        when(cancion1.getAlbum()).thenReturn(album);
        when(album.getUrlFoto()).thenReturn("http://foto-album.jpg");
        when(album.getNombre()).thenReturn("Nombre del Álbum");
        when(cancion1.getCategoriaMusical()).thenReturn(categoria);
        when(categoria.getNombre()).thenReturn("Pop");

        when(usuario.getNombreUsuario()).thenReturn("artista123");
        when(artista.getUsuario()).thenReturn(usuario);
        when(pac.getPerfilArtista()).thenReturn(artista);

        List<PerfilArtista_Cancion> listaPAC = List.of(pac);
        when(cancion1.getPerfilArtista_CancionList()).thenReturn(listaPAC);

        when(cancionRepository.findByEstadoAndNombreContainingIgnoreCase("publica", "Mi"))
                .thenReturn(List.of(cancion1));

        List<BusquedaCancionDTO> resultado = cancionService.buscarCancionesPorNombre("Mi");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        BusquedaCancionDTO dto = resultado.get(0);
        assertEquals(1, dto.getIdCancion());
        assertEquals("Mi Canción", dto.getNombre());
        assertEquals("00:03:30", dto.getDuracion());
        assertEquals("http://archivo.mp3", dto.getUrlArchivo());
        assertEquals("http://foto-album.jpg", dto.getUrlFoto());
        assertEquals("artista123", dto.getNombreArtista());
        assertEquals("2023-06-20", dto.getFechaPublicacion());
        assertEquals("Nombre del Álbum", dto.getNombreAlbum());
        assertEquals("Pop", dto.getCategoriaMusical());

        verify(cancionRepository, times(1)).findByEstadoAndNombreContainingIgnoreCase("publica", "Mi");
    }

    @Test
    public void buscarCancionesPorNombre_deberiaRetornarListaVaciaSiNoEncuentra() {
        when(cancionRepository.findByEstadoAndNombreContainingIgnoreCase("publica", "xxx"))
                .thenReturn(Collections.emptyList());

        List<BusquedaCancionDTO> resultado = cancionService.buscarCancionesPorNombre("xxx");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(cancionRepository).findByEstadoAndNombreContainingIgnoreCase("publica", "xxx");
    }

    @Test
    public void obtenerCancionesPorIdAlbum_deberiaRetornarListaDeCancionesCorrectamente() {
        Album album = mock(Album.class);
        Cancion cancion = mock(Cancion.class);
        CategoriaMusical categoria = mock(CategoriaMusical.class);
        PerfilArtista artista = mock(PerfilArtista.class);
        Usuario usuario = mock(Usuario.class);
        PerfilArtista_Cancion pac = mock(PerfilArtista_Cancion.class);

        when(albumRepository.findByIdAlbum(1)).thenReturn(album);
        when(cancionRepository.findByAlbum(album)).thenReturn(List.of(cancion));

        when(cancion.getIdCancion()).thenReturn(10);
        when(cancion.getNombre()).thenReturn("Nombre Canción");
        when(cancion.getDuracion()).thenReturn(LocalTime.of(0, 2, 45));
        when(cancion.getUrlArchivo()).thenReturn("http://archivo-cancion.mp3");
        when(cancion.getUrlFoto()).thenReturn(null);
        when(cancion.getFechaPublicacion()).thenReturn(LocalDate.of(2023, 5, 10));
        when(cancion.getAlbum()).thenReturn(album);
        when(cancion.getCategoriaMusical()).thenReturn(categoria);
        when(categoria.getNombre()).thenReturn("Rock");

        when(album.getUrlFoto()).thenReturn("http://foto-album.jpg");
        when(album.getNombre()).thenReturn("Álbum Épico");

        when(usuario.getNombreUsuario()).thenReturn("artistaTest");
        when(artista.getUsuario()).thenReturn(usuario);
        when(pac.getPerfilArtista()).thenReturn(artista);
        when(cancion.getPerfilArtista_CancionList()).thenReturn(List.of(pac));

        List<BusquedaCancionDTO> resultado = cancionService.obtenerCancionesPorIdAlbum(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        BusquedaCancionDTO dto = resultado.get(0);
        assertEquals(10, dto.getIdCancion());
        assertEquals("Nombre Canción", dto.getNombre());
        assertEquals("00:02:45", dto.getDuracion());
        assertEquals("http://archivo-cancion.mp3", dto.getUrlArchivo());
        assertEquals("http://foto-album.jpg", dto.getUrlFoto());
        assertEquals("artistaTest", dto.getNombreArtista());
        assertEquals("2023-05-10", dto.getFechaPublicacion());
        assertEquals("Álbum Épico", dto.getNombreAlbum());
        assertEquals("Rock", dto.getCategoriaMusical());

        verify(albumRepository).findByIdAlbum(1);
        verify(cancionRepository).findByAlbum(album);
    }

    @Test
    public void obtenerCancionesPorIdAlbum_deberiaLanzarExcepcionSiAlbumNoExiste() {
        when(albumRepository.findByIdAlbum(999)).thenReturn(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            cancionService.obtenerCancionesPorIdAlbum(999);
        });

        assertEquals("Álbum no encontrado.", ex.getMessage());
        verify(albumRepository).findByIdAlbum(999);
        verifyNoInteractions(cancionRepository);
    }

    @Test
    public void obtenerSencillosPorIdArtista_deberiaRetornarListaDeCancionesCorrectamente() {
        PerfilArtista artista = mock(PerfilArtista.class);
        Cancion cancion = mock(Cancion.class);
        CategoriaMusical categoria = mock(CategoriaMusical.class);
        PerfilArtista_Cancion pac = mock(PerfilArtista_Cancion.class);
        Usuario usuario = mock(Usuario.class);

        when(perfilArtistaRepository.findByIdPerfilArtista(1)).thenReturn(artista);
        when(cancionRepository.findByEstadoAndArtistaId("publica", 1)).thenReturn(List.of(cancion));

        when(cancion.getIdCancion()).thenReturn(101);
        when(cancion.getNombre()).thenReturn("Sencillo X");
        when(cancion.getDuracion()).thenReturn(LocalTime.of(0, 4, 0));
        when(cancion.getUrlArchivo()).thenReturn("http://sencillo.mp3");
        when(cancion.getUrlFoto()).thenReturn("http://foto-sencillo.jpg");
        when(cancion.getFechaPublicacion()).thenReturn(LocalDate.of(2024, 1, 15));
        when(cancion.getAlbum()).thenReturn(null); // No tiene álbum
        when(cancion.getCategoriaMusical()).thenReturn(categoria);
        when(categoria.getNombre()).thenReturn("Indie");

        when(usuario.getNombreUsuario()).thenReturn("artistaTop");
        when(pac.getPerfilArtista()).thenReturn(artista);
        when(artista.getUsuario()).thenReturn(usuario);
        when(cancion.getPerfilArtista_CancionList()).thenReturn(List.of(pac));

        List<BusquedaCancionDTO> resultado = cancionService.obtenerSencillosPorIdArtista(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        BusquedaCancionDTO dto = resultado.get(0);
        assertEquals(101, dto.getIdCancion());
        assertEquals("Sencillo X", dto.getNombre());
        assertEquals("00:04", dto.getDuracion());
        assertEquals("http://sencillo.mp3", dto.getUrlArchivo());
        assertEquals("http://foto-sencillo.jpg", dto.getUrlFoto());
        assertEquals("artistaTop", dto.getNombreArtista());
        assertEquals("2024-01-15", dto.getFechaPublicacion());
        assertNull(dto.getNombreAlbum()); // No tiene álbum
        assertEquals("Indie", dto.getCategoriaMusical());

        verify(perfilArtistaRepository).findByIdPerfilArtista(1);
        verify(cancionRepository).findByEstadoAndArtistaId("publica", 1);
    }

    @Test
    public void obtenerSencillosPorIdArtista_deberiaLanzarExcepcionSiArtistaNoExiste() {
        when(perfilArtistaRepository.findByIdPerfilArtista(999)).thenReturn(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            cancionService.obtenerSencillosPorIdArtista(999);
        });

        assertEquals("Artista no encontrado.", ex.getMessage());
        verify(perfilArtistaRepository).findByIdPerfilArtista(999);
        verifyNoInteractions(cancionRepository);
    }

    @Test
    public void eliminarCancion_sinArchivos_deberiaEliminarCorrectamente() {
        Cancion cancion = mock(Cancion.class);
        when(cancion.getUrlArchivo()).thenReturn(null);
        when(cancion.getUrlFoto()).thenReturn(null);
        when(cancionRepository.findById(10)).thenReturn(Optional.of(cancion));

        String resultado = cancionService.eliminarCancion(10);

        assertEquals("Canción eliminada correctamente.", resultado);
        verify(perfilArtista_CancionRepository).deleteByCancion(cancion);
        verify(listaDeReproduccion_CancionRepository).deleteByCancion(cancion);
        verify(cancionRepository).delete(cancion);
    }

}

