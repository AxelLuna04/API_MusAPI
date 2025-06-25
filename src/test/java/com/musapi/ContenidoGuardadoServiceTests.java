/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi;

import com.musapi.dto.ContenidoGuardadoDTO;
import com.musapi.enums.TipoContenido;
import com.musapi.model.Album;
import com.musapi.model.ContenidoGuardado;
import com.musapi.model.ListaDeReproduccion;
import com.musapi.model.PerfilArtista;
import com.musapi.model.Usuario;
import com.musapi.repository.AlbumRepository;
import com.musapi.repository.CancionRepository;
import com.musapi.repository.ContenidoGuardadoRepository;
import com.musapi.repository.ListaDeReproduccionRepository;
import com.musapi.repository.PerfilArtistaRepository;
import com.musapi.repository.UsuarioRepository;
import com.musapi.service.ContenidoGuardadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ContenidoGuardadoServiceTests {

    @InjectMocks
    private ContenidoGuardadoService service;

    @Mock private ContenidoGuardadoRepository contenidoGuardadoRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private AlbumRepository albumRepository;
    @Mock private CancionRepository cancionRepository;
    @Mock private ListaDeReproduccionRepository listaRepository;
    @Mock private PerfilArtistaRepository perfilArtistaRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private Usuario crearUsuario(Integer id) {
        Usuario u = new Usuario();
        u.setIdUsuario(id);
        return u;
    }

    @Test
    public void guardarContenido_albumValido_deberiaGuardarYRetornarExito() {
        ContenidoGuardadoDTO dto = new ContenidoGuardadoDTO(1, 5, TipoContenido.ALBUM);
        Usuario usuario = crearUsuario(1);
        Album album = new Album();

        when(usuarioRepository.findByIdUsuario(1)).thenReturn(usuario);
        when(albumRepository.findByIdAlbum(5)).thenReturn(album);
        when(contenidoGuardadoRepository.findByUsuarioAndAlbum(usuario, album)).thenReturn(null);

        String resultado = service.guardarContenido(dto);
        assertEquals("Contenido guardado exitosamente", resultado);
        verify(contenidoGuardadoRepository).save(any());
    }

    @Test
    public void guardarContenido_albumYaGuardado_deberiaRetornarMensaje() {
        ContenidoGuardadoDTO dto = new ContenidoGuardadoDTO(1, 5, TipoContenido.ALBUM);
        Usuario usuario = crearUsuario(1);
        Album album = new Album();

        when(usuarioRepository.findByIdUsuario(1)).thenReturn(usuario);
        when(albumRepository.findByIdAlbum(5)).thenReturn(album);
        when(contenidoGuardadoRepository.findByUsuarioAndAlbum(usuario, album)).thenReturn(new ContenidoGuardado());

        String resultado = service.guardarContenido(dto);
        assertEquals("Este álbum ya se encuentra en tus contenidos guardados", resultado);
    }


    @Test
    public void guardarContenido_cancionNoExiste_deberiaRetornarMensaje() {
        ContenidoGuardadoDTO dto = new ContenidoGuardadoDTO(1, 99, TipoContenido.CANCION);
        when(usuarioRepository.findByIdUsuario(1)).thenReturn(crearUsuario(1));
        when(cancionRepository.findByIdCancion(99)).thenReturn(null);

        String resultado = service.guardarContenido(dto);
        assertEquals("Contenido no encontrado", resultado);
    }


    @Test
    public void guardarContenido_listaDuplicada_deberiaRetornarMensaje() {
        ContenidoGuardadoDTO dto = new ContenidoGuardadoDTO(1, 7, TipoContenido.LISTA);
        Usuario usuario = crearUsuario(1);
        ListaDeReproduccion lista = new ListaDeReproduccion();

        when(usuarioRepository.findByIdUsuario(1)).thenReturn(usuario);
        when(listaRepository.findByIdListaDeReproduccion(7)).thenReturn(lista);
        when(contenidoGuardadoRepository.findByUsuarioAndListaDeReproduccion(usuario, lista)).thenReturn(new ContenidoGuardado());

        String resultado = service.guardarContenido(dto);
        assertEquals("Esta lsita ya se encuentra en tus contenidos guardados", resultado);
    }


    @Test
    public void guardarContenido_artistaValido_deberiaGuardarCorrectamente() {
        ContenidoGuardadoDTO dto = new ContenidoGuardadoDTO(1, 10, TipoContenido.ARTISTA);
        Usuario usuario = crearUsuario(1);
        PerfilArtista artista = new PerfilArtista();

        when(usuarioRepository.findByIdUsuario(1)).thenReturn(usuario);
        when(perfilArtistaRepository.findByIdPerfilArtista(10)).thenReturn(artista);
        when(contenidoGuardadoRepository.findByUsuarioAndPerfilArtista(usuario, artista)).thenReturn(null);

        String resultado = service.guardarContenido(dto);
        assertEquals("Contenido guardado exitosamente", resultado);
        verify(contenidoGuardadoRepository).save(any());
    }

    @Test
    public void guardarContenido_usuarioNoExiste_deberiaRetornarMensaje() {
        ContenidoGuardadoDTO dto = new ContenidoGuardadoDTO(100, 2, TipoContenido.CANCION);
        when(usuarioRepository.findByIdUsuario(100)).thenReturn(null);

        String resultado = service.guardarContenido(dto);
        assertEquals("Usuario no encontrado", resultado);
    }

    @Test
    public void guardarContenido_tipoInvalido_deberiaRetornarMensaje() {
        ContenidoGuardadoDTO dto = new ContenidoGuardadoDTO(1, 10, null);
        when(usuarioRepository.findByIdUsuario(1)).thenReturn(crearUsuario(1));

        String resultado = service.guardarContenido(dto);
        assertEquals("Tipo de contenido no disponible", resultado);
    }
}
