package com.musapi;

import com.musapi.dto.BusquedaArtistaDTO;
import com.musapi.dto.BusquedaCancionDTO;
import com.musapi.dto.EdicionPerfilDTO;
import com.musapi.dto.PerfilArtistaDTO;
import com.musapi.model.PerfilArtista;
import com.musapi.model.PerfilArtista_Cancion;
import com.musapi.model.Usuario;
import com.musapi.model.Cancion;
import com.musapi.model.CategoriaMusical;
import com.musapi.repository.PerfilArtistaRepository;
import com.musapi.repository.UsuarioRepository;
import com.musapi.service.CorreoService;
import com.musapi.service.UsuarioService;
import com.musapi.service.CancionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTests {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PerfilArtistaRepository perfilArtistaRepository;

    @Mock
    private CorreoService correoService;

    @Mock
    private CancionService cancionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void eliminarUsuario_deberiaEnviarCorreoYEliminarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("test@musapi.com");
        usuario.setNombreUsuario("usuarioX");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        usuarioService.eliminarUsuario(1, "Violación de términos");

        verify(correoService).enviarCorreo(
                eq("test@musapi.com"),
                contains("Cuenta eliminada"),
                contains("Violación de términos")
        );
        verify(usuarioRepository).delete(usuario);
    }

    @Test
    public void buscarUsuarios_deberiaRetornarDTOsCorrectamente() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setNombreUsuario("jdoe");
        usuario.setNombre("John Doe");
        usuario.setCorreo("jdoe@musapi.com");
        usuario.setEsAdmin(false);
        usuario.setEsArtista(false);
        usuario.setPais("MX");

        when(usuarioRepository.buscarUsuariosPorNombreOUsuario("jdoe", 99))
                .thenReturn(List.of(usuario));

        var resultado = usuarioService.buscarUsuarios("jdoe", 99);

        assertEquals(1, resultado.size());
        assertEquals("jdoe", resultado.get(0).getNombreUsuario());
        assertEquals("MX", resultado.get(0).getPais());
        verify(usuarioRepository).buscarUsuariosPorNombreOUsuario("jdoe", 99);
    }

    @Test
    public void obtenerArtistaPorId_deberiaRetornarDTOConCanciones() {
        Usuario usuario = mock(Usuario.class);
        PerfilArtista perfil = mock(PerfilArtista.class);
        CategoriaMusical categoria = mock(CategoriaMusical.class);
        Cancion cancion = mock(Cancion.class);
        PerfilArtista_Cancion pac = mock(PerfilArtista_Cancion.class);

        when(usuarioRepository.findByIdUsuarioAndEsArtistaTrue(1)).thenReturn(usuario);
        when(usuario.getPerfilArtista()).thenReturn(perfil);
        when(usuario.getNombre()).thenReturn("Artista X");
        when(usuario.getNombreUsuario()).thenReturn("artx");

        when(perfil.getIdPerfilArtista()).thenReturn(5);
        when(perfil.getDescripcion()).thenReturn("desc");
        when(perfil.getUrlFoto()).thenReturn("url.jpg");

        when(cancion.getIdCancion()).thenReturn(10);
        when(cancion.getNombre()).thenReturn("Canción Z");
        when(cancion.getDuracion()).thenReturn(LocalTime.of(0, 3, 15));
        when(cancion.getUrlArchivo()).thenReturn("url.mp3");
        when(cancion.getUrlFoto()).thenReturn("foto.jpg");
        when(cancion.getFechaPublicacion()).thenReturn(LocalDate.of(2023, 1, 1));
        when(cancion.getAlbum()).thenReturn(null);
        when(cancion.getCategoriaMusical()).thenReturn(categoria);
        when(categoria.getNombre()).thenReturn("Jazz");

        when(pac.getCancion()).thenReturn(cancion);
        when(perfil.getPerfilArtista_CancionList()).thenReturn(List.of(pac));

        BusquedaArtistaDTO dto = usuarioService.obtenerArtistaPorId(1);

        assertEquals(5, dto.getIdArtista());
        assertEquals("artx", dto.getNombreUsuario());
        assertEquals(1, dto.getCanciones().size());
        assertEquals("Canción Z", dto.getCanciones().get(0).getNombre());
    }

    @Test
    public void editarPerfil_deberiaActualizarDatosBasicosDelUsuario() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setEsArtista(false);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        EdicionPerfilDTO dto = new EdicionPerfilDTO();
        dto.setNombre("Nuevo Nombre");
        dto.setNombreUsuario("nuevoUsuario");
        dto.setPais("AR");

        boolean resultado = usuarioService.editarPerfil(1, dto);

        assertTrue(resultado);
        assertEquals("Nuevo Nombre", usuario.getNombre());
        assertEquals("nuevoUsuario", usuario.getNombreUsuario());
        assertEquals("AR", usuario.getPais());
        verify(usuarioRepository).save(usuario);
    }
}
