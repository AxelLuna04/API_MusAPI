package com.musapi;

import com.musapi.dto.ContenidoDTO;
import com.musapi.enums.TipoContenido;
import com.musapi.model.*;
import com.musapi.repository.*;
import com.musapi.service.ContenidoService;
import com.musapi.service.CorreoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ContenidoServiceTests {

    @InjectMocks
    private ContenidoService contenidoService;

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private CancionRepository cancionRepository;

    @Mock
    private PerfilArtistaRepository perfilArtistaRepository;

    @Mock
    private CorreoService correoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void eliminarContenido_deberiaEliminarCancionYNotificar() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("artista@musapi.com");

        PerfilArtista perfil = new PerfilArtista();
        perfil.setUsuario(usuario);

        PerfilArtista_Cancion relacion = new PerfilArtista_Cancion();
        relacion.setPerfilArtista(perfil);

        Cancion cancion = new Cancion();
        cancion.setNombre("Canción de prueba");
        cancion.setPerfilArtista_CancionList(List.of(relacion));

        when(cancionRepository.findById(10)).thenReturn(Optional.of(cancion));

        ContenidoDTO dto = new ContenidoDTO();
        dto.setIdContenido(10);
        dto.setTipoContendio(TipoContenido.CANCION);
        dto.setMotivoEliminacion("Contenido inapropiado");

        contenidoService.eliminarContenido(dto);

        verify(correoService).enviarCorreo(
                eq("artista@musapi.com"),
                contains("ha sido eliminada"),
                contains("Contenido inapropiado")
        );
        verify(cancionRepository).delete(cancion);
    }

    @Test
    public void eliminarContenido_deberiaEliminarAlbumYNotificar() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("album@musapi.com");

        PerfilArtista artista = new PerfilArtista();
        artista.setUsuario(usuario);

        Album album = new Album();
        album.setIdAlbum(5);
        album.setNombre("Álbum de prueba");
        album.setPerfilArtista(artista);

        when(albumRepository.findById(5)).thenReturn(Optional.of(album));

        ContenidoDTO dto = new ContenidoDTO();
        dto.setIdContenido(5);
        dto.setTipoContendio(TipoContenido.ALBUM);
        dto.setMotivoEliminacion("Violación de derechos");

        contenidoService.eliminarContenido(dto);

        verify(correoService).enviarCorreo(
                eq("album@musapi.com"),
                contains("ha sido eliminado"),
                contains("Violación de derechos")
        );
        verify(albumRepository).delete(album);
    }

    @Test
    public void eliminarContenido_deberiaEliminarArtistaYNotificar() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("artista@musapi.com");

        PerfilArtista perfil = new PerfilArtista();
        perfil.setUsuario(usuario);

        when(perfilArtistaRepository.findById(7)).thenReturn(Optional.of(perfil));

        ContenidoDTO dto = new ContenidoDTO();
        dto.setIdContenido(7);
        dto.setTipoContendio(TipoContenido.ARTISTA);
        dto.setMotivoEliminacion("Cuenta falsa");

        contenidoService.eliminarContenido(dto);

        verify(correoService).enviarCorreo(
                eq("artista@musapi.com"),
                contains("perfil de artista ha sido eliminado"),
                contains("Cuenta falsa")
        );
        verify(perfilArtistaRepository).delete(perfil);
    }

    /*@Test
    public void eliminarContenido_conTipoInvalido_deberiaLanzarExcepcion() {
        ContenidoDTO dto = new ContenidoDTO();
        dto.setIdContenido(99);
        dto.setTipoContendio(null);
        dto.setMotivoEliminacion("Error");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            contenidoService.eliminarContenido(dto);
        });

        assertEquals("Tipo de contenido no válido para eliminación.", ex.getMessage());
    }*/

}
