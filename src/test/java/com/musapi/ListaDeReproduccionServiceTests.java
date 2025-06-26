/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi;

import com.musapi.dto.CreacionListaDeReproduccionDTO;
import com.musapi.dto.ListaDeReproduccion_CancionDTO;
import com.musapi.model.Cancion;
import com.musapi.model.ListaDeReproduccion;
import com.musapi.model.ListaDeReproduccion_Cancion;
import com.musapi.model.Usuario;
import com.musapi.repository.CancionRepository;
import com.musapi.repository.ListaDeReproduccionRepository;
import com.musapi.repository.UsuarioRepository;
import com.musapi.service.ListaDeReproduccionService;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.multipart.MultipartFile;

public class ListaDeReproduccionServiceTests {

    @InjectMocks
    private ListaDeReproduccionService listaDeReproduccionService;  // la clase donde está el método crearListaDeReproduccion

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ListaDeReproduccionRepository listaDeReproduccionRepository;
    
    @Mock
    private CancionRepository cancionRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void crearListaDeReproduccion_usuarioNoEncontrado_deberiaRetornarMensajeError() {
        CreacionListaDeReproduccionDTO dto = new CreacionListaDeReproduccionDTO();
        dto.setIdUsuario(999);
        dto.setNombre("Mi Playlist");
        dto.setDescripcion("Descripción");
        dto.setFoto(null);

        when(usuarioRepository.findByIdUsuario(999)).thenReturn(null);

        String resultado = listaDeReproduccionService.crearListaDeReproduccion(dto);

        assertEquals("Usuario no encontrado.", resultado);
        verify(usuarioRepository).findByIdUsuario(999);
        verifyNoInteractions(listaDeReproduccionRepository);
    }

    @Test
    public void crearListaDeReproduccion_usuarioExistenteSinFoto_deberiaGuardarCorrectamente() throws IOException {
        Usuario usuario = mock(Usuario.class);
        CreacionListaDeReproduccionDTO dto = new CreacionListaDeReproduccionDTO();
        dto.setIdUsuario(1);
        dto.setNombre("Mi Playlist");
        dto.setDescripcion("Descripción");
        dto.setFoto(null);

        when(usuarioRepository.findByIdUsuario(1)).thenReturn(usuario);

        String resultado = listaDeReproduccionService.crearListaDeReproduccion(dto);

        assertEquals("Lista creada con éxito", resultado);

        ArgumentCaptor<ListaDeReproduccion> captor = ArgumentCaptor.forClass(ListaDeReproduccion.class);
        verify(listaDeReproduccionRepository).save(captor.capture());

        ListaDeReproduccion guardada = captor.getValue();
        assertEquals("Mi Playlist", guardada.getNombre());
        assertEquals("Descripción", guardada.getDescripcion());
        assertEquals(usuario, guardada.getUsuario());
        assertNull(guardada.getUrlFoto());

        verify(usuarioRepository).findByIdUsuario(1);
    }

    @Test
    public void crearListaDeReproduccion_usuarioExistenteConFoto_deberiaGuardarFotoYLista() throws IOException {
        Usuario usuario = mock(Usuario.class);
        MultipartFile fotoMock = mock(MultipartFile.class);

        CreacionListaDeReproduccionDTO dto = new CreacionListaDeReproduccionDTO();
        dto.setIdUsuario(1);
        dto.setNombre("Playlist Con Foto");
        dto.setDescripcion("Descripción con foto");
        dto.setFoto(fotoMock);

        when(usuarioRepository.findByIdUsuario(1)).thenReturn(usuario);

        doNothing().when(fotoMock).transferTo(any(File.class));

        String resultado = listaDeReproduccionService.crearListaDeReproduccion(dto);

        assertEquals("Lista creada con éxito", resultado);

        ArgumentCaptor<ListaDeReproduccion> captor = ArgumentCaptor.forClass(ListaDeReproduccion.class);
        verify(listaDeReproduccionRepository).save(captor.capture());

        ListaDeReproduccion guardada = captor.getValue();
        assertEquals("Playlist Con Foto", guardada.getNombre());
        assertEquals("Descripción con foto", guardada.getDescripcion());
        assertEquals(usuario, guardada.getUsuario());
        assertNotNull(guardada.getUrlFoto());
        assertTrue(guardada.getUrlFoto().contains("/uploads/fotos-listasDeReproduccion/foto_"));

        verify(usuarioRepository).findByIdUsuario(1);
        verify(fotoMock).transferTo(any(File.class));
    }

    @Test
    public void crearListaDeReproduccion_errorAlGuardarFoto_deberiaLanzarExcepcion() throws IOException {
        Usuario usuario = mock(Usuario.class);
        MultipartFile fotoMock = mock(MultipartFile.class);

        CreacionListaDeReproduccionDTO dto = new CreacionListaDeReproduccionDTO();
        dto.setIdUsuario(1);
        dto.setNombre("Playlist Error Foto");
        dto.setDescripcion("Descripción");
        dto.setFoto(fotoMock);

        when(usuarioRepository.findByIdUsuario(1)).thenReturn(usuario);

        doThrow(new IOException("Fallo al guardar archivo")).when(fotoMock).transferTo(any(File.class));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            listaDeReproduccionService.crearListaDeReproduccion(dto);
        });

        assertTrue(thrown.getMessage().contains("Error al guardar la imagen"));

        verify(usuarioRepository).findByIdUsuario(1);
        verify(fotoMock).transferTo(any(File.class));
        verifyNoInteractions(listaDeReproduccionRepository);
    }
    
    /*@Test
    public void agregarCancionALista_listaNoExiste_deberiaRetornarMensaje() {
        ListaDeReproduccion_CancionDTO dto = new ListaDeReproduccion_CancionDTO();
        dto.setIdListaDeReproduccion(1);
        when(listaDeReproduccionRepository.findByIdListaDeReproduccion(1)).thenReturn(null);

        String resultado = listaDeReproduccionService.agregarCancionALista(dto);
        assertEquals("La lista de reproducción no existe", resultado);
    }*/

    @Test
    public void agregarCancionALista_listaNoPerteneceAlUsuario_deberiaRetornarMensaje() {
        ListaDeReproduccion_CancionDTO dto = new ListaDeReproduccion_CancionDTO();
        dto.setIdListaDeReproduccion(1);
        dto.setIdUsuario(99);

        Usuario otroUsuario = new Usuario();
        otroUsuario.setIdUsuario(1);

        ListaDeReproduccion lista = new ListaDeReproduccion();
        lista.setUsuario(otroUsuario);

        when(listaDeReproduccionRepository.findByIdListaDeReproduccion(1)).thenReturn(lista);

        String resultado = listaDeReproduccionService.agregarCancionALista(dto);
        assertEquals("La lista no pertenece al usuario", resultado);
    }

    @Test
    public void agregarCancionALista_cancionNoExiste_deberiaRetornarMensaje() {
        ListaDeReproduccion_CancionDTO dto = new ListaDeReproduccion_CancionDTO();
        dto.setIdListaDeReproduccion(1);
        dto.setIdUsuario(1);
        dto.setIdCancion(123);

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);

        ListaDeReproduccion lista = new ListaDeReproduccion();
        lista.setUsuario(usuario);

        when(listaDeReproduccionRepository.findByIdListaDeReproduccion(1)).thenReturn(lista);
        when(cancionRepository.findByIdCancion(123)).thenReturn(null);

        String resultado = listaDeReproduccionService.agregarCancionALista(dto);
        assertEquals("La canción no existe", resultado);
    }

    @Test
    public void agregarCancionALista_cancionYaExisteEnLista_deberiaRetornarMensaje() {
        ListaDeReproduccion_CancionDTO dto = new ListaDeReproduccion_CancionDTO();
        dto.setIdListaDeReproduccion(1);
        dto.setIdUsuario(1);
        dto.setIdCancion(10);

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);

        Cancion cancion = new Cancion();
        cancion.setIdCancion(10);

        ListaDeReproduccion lista = new ListaDeReproduccion();
        lista.setUsuario(usuario);
        lista.setListaDeReproduccion_CancionList(new ArrayList<>());

        ListaDeReproduccion_Cancion relacion = new ListaDeReproduccion_Cancion();
        relacion.setCancion(cancion);
        lista.getListaDeReproduccion_CancionList().add(relacion);

        when(listaDeReproduccionRepository.findByIdListaDeReproduccion(1)).thenReturn(lista);
        when(cancionRepository.findByIdCancion(10)).thenReturn(cancion);

        String resultado = listaDeReproduccionService.agregarCancionALista(dto);
        assertEquals("La canción ya se encuentra agregada en esa lista.", resultado);
    }

    @Test
    public void agregarCancionALista_datosCorrectos_deberiaAgregarYGuardar() {
        ListaDeReproduccion_CancionDTO dto = new ListaDeReproduccion_CancionDTO();
        dto.setIdListaDeReproduccion(1);
        dto.setIdUsuario(1);
        dto.setIdCancion(10);

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);

        Cancion cancion = new Cancion();
        cancion.setIdCancion(10);

        ListaDeReproduccion lista = new ListaDeReproduccion();
        lista.setUsuario(usuario);
        lista.setListaDeReproduccion_CancionList(new ArrayList<>());

        when(listaDeReproduccionRepository.findByIdListaDeReproduccion(1)).thenReturn(lista);
        when(cancionRepository.findByIdCancion(10)).thenReturn(cancion);

        String resultado = listaDeReproduccionService.agregarCancionALista(dto);

        assertEquals("Canción agregada correctamente", resultado);
        assertEquals(1, lista.getListaDeReproduccion_CancionList().size());
        ListaDeReproduccion_Cancion agregada = lista.getListaDeReproduccion_CancionList().get(0);
        assertEquals(10, agregada.getCancion().getIdCancion());
        assertEquals(1, agregada.getPosicionCancion());

        verify(listaDeReproduccionRepository).save(lista);
    }
}
