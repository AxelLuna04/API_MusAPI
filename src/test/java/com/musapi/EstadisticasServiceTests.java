package com.musapi;

import com.musapi.dto.ArtistaMasEscuchadoDTO;
import com.musapi.dto.CancionMasEscuchadaDTO;
import com.musapi.dto.EstadisticasContenidoSubidoDTO;
import com.musapi.dto.EstadisticasNumeroUsuariosDTO;
import com.musapi.dto.EstadisticasPersonalesDTO;
import com.musapi.model.*;
import com.musapi.repository.*;

import com.musapi.service.EstadisticasService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EstadisticasServiceTests {

    @InjectMocks
    private EstadisticasService estadisticasService;

    @Mock
    private PerfilArtistaRepository perfilArtistaRepository;

    @Mock
    private EscuchaRepository escuchaRepository;

    @Mock
    private ContenidoGuardadoRepository contenidoGuardadoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ListaDeReproduccion_CancionRepository lrcRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void obtenerEstadisticas_conTipoCancion_deberiaRetornarDTO() {
        PerfilArtista artista = new PerfilArtista();
        artista.setIdPerfilArtista(1);

        when(perfilArtistaRepository.findById(1)).thenReturn(Optional.of(artista));
        when(escuchaRepository.countUsuariosUnicosQueEscucharonArtista(1)).thenReturn(5);
        when(escuchaRepository.countAparicionesEnListasDeReproduccion(1)).thenReturn(8);

        EstadisticasContenidoSubidoDTO dto = estadisticasService.obtenerEstadisticas(1, "Cancion");

        assertEquals(5, dto.getNumeroOyentes());
        assertEquals(8, dto.getNumeroGuardados());
    }

    @Test
    public void obtenerEstadisticas_conTipoAlbum_deberiaRetornarDTO() {
        PerfilArtista artista = new PerfilArtista();
        Album album = new Album();
        album.setIdAlbum(42);
        artista.setAlbumes(List.of(album));

        Set<Integer> albumIds = Set.of(42);

        when(perfilArtistaRepository.findById(2)).thenReturn(Optional.of(artista));
        when(escuchaRepository.countUsuariosUnicosQueEscucharonArtista(2)).thenReturn(10);
        when(contenidoGuardadoRepository.findUsuariosByAlbumIds(albumIds)).thenReturn((Set<Integer>) List.of(1, 2, 3));

        EstadisticasContenidoSubidoDTO dto = estadisticasService.obtenerEstadisticas(2, "Album");

        assertEquals(10, dto.getNumeroOyentes());
        assertEquals(3, dto.getNumeroGuardados());
    }

    @Test
    public void obtenerConteoUsuariosYArtistas_deberiaContarCorrectamente() {
        Usuario u1 = new Usuario(); u1.setEsArtista(true);
        Usuario u2 = new Usuario(); u2.setEsArtista(false);
        Usuario u3 = new Usuario(); u3.setEsArtista(false);

        when(usuarioRepository.findAll()).thenReturn(List.of(u1, u2, u3));

        EstadisticasNumeroUsuariosDTO dto = estadisticasService.obtenerConteoUsuariosYArtistas();

        assertEquals(1, dto.getTotalArtistas());
        assertEquals(2, dto.getTotalUsuarios());
    }

    @Test
    public void obtenerEstadisticasPersonales_deberiaConstruirDatosCorrectamente() {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("u1");

        PerfilArtista perfil = new PerfilArtista();
        perfil.setUsuario(usuario);

        Cancion c1 = new Cancion();
        c1.setNombre("C1");
        PerfilArtista_Cancion pac = new PerfilArtista_Cancion();
        pac.setPerfilArtista(perfil);
        c1.setPerfilArtista_CancionList(List.of(pac));

        Escucha e1 = new Escucha();
        e1.setCancion(c1);
        e1.setTiempoEscucha(LocalTime.of(0, 3, 0));

        when(escuchaRepository.findByUsuario_IdUsuarioAndFechaEscuchaBetween(anyInt(), any(), any()))
                .thenReturn(List.of(e1));

        EstadisticasPersonalesDTO dto = estadisticasService.obtenerEstadisticasPersonales(1, LocalDate.now().minusDays(1), LocalDate.now());

        assertEquals(180, dto.getSegundosEscuchados());
        assertEquals(1, dto.getTopCanciones().size());
        assertTrue(dto.getTopCanciones().get(0).contains("C1"));
        assertEquals(List.of("u1"), dto.getTopArtistas());
    }

    @Test
    public void obtenerTop10ArtistasMasEscuchados_deberiaRetornarArtistas() {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("artist1");

        PerfilArtista perfil = new PerfilArtista();
        perfil.setUsuario(usuario);

        PerfilArtista_Cancion pac = new PerfilArtista_Cancion();
        pac.setPerfilArtista(perfil);

        Cancion cancion = new Cancion();
        cancion.setPerfilArtista_CancionList(List.of(pac));

        Escucha escucha = new Escucha();
        escucha.setCancion(cancion);
        escucha.setTiempoEscucha(LocalTime.of(0, 2, 0));

        when(escuchaRepository.findByFechaEscuchaBetween(any(), any()))
                .thenReturn(List.of(escucha));

        List<ArtistaMasEscuchadoDTO> lista = estadisticasService.obtenerTop10ArtistasMasEscuchados(LocalDate.now().minusDays(7), LocalDate.now());

        assertEquals(1, lista.size());
        assertEquals("artist1", lista.get(0).getNombreArtista());
        assertEquals(120L, lista.get(0).getSegundosEscuchados());
    }

    @Test
    public void obtenerTop10CancionesMasEscuchadas_deberiaRetornarCanciones() {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("artistaX");

        PerfilArtista perfil = new PerfilArtista();
        perfil.setUsuario(usuario);

        PerfilArtista_Cancion pac = new PerfilArtista_Cancion();
        pac.setPerfilArtista(perfil);

        Cancion cancion = new Cancion();
        cancion.setNombre("Mi Canción");
        cancion.setPerfilArtista_CancionList(List.of(pac));

        Escucha escucha = new Escucha();
        escucha.setCancion(cancion);
        escucha.setTiempoEscucha(LocalTime.of(0, 5, 0)); // 300 segundos

        when(escuchaRepository.findByFechaEscuchaBetween(any(), any()))
                .thenReturn(List.of(escucha));

        List<CancionMasEscuchadaDTO> top = estadisticasService.obtenerTop10CancionesMasEscuchadas(LocalDate.now().minusDays(30), LocalDate.now());

        assertEquals(1, top.size());
        assertEquals("Mi Canción", top.get(0).getNombreCancion());
        assertEquals("artistaX", top.get(0).getNombreUsuarioArtista());
        assertEquals(300, top.get(0).getSegundosEscuchados());
    }
}
