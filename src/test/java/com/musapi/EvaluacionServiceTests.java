package com.musapi;

import com.musapi.dto.EvaluacionArtistaDTO;
import com.musapi.model.Evaluacion;
import com.musapi.model.PerfilArtista;
import com.musapi.model.Usuario;
import com.musapi.repository.EvaluacionRepository;
import com.musapi.repository.PerfilArtistaRepository;
import com.musapi.repository.UsuarioRepository;
import com.musapi.service.EvaluacionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EvaluacionServiceTests {

    @InjectMocks
    private EvaluacionService evaluacionService;

    @Mock
    private EvaluacionRepository evaluacionRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PerfilArtistaRepository perfilArtistaRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void evaluarArtista_deberiaRegistrarEvaluacionCorrectamente() {
        EvaluacionArtistaDTO dto = new EvaluacionArtistaDTO();
        dto.setIdUsuario(1);
        dto.setIdArtista(2);
        dto.setComentario("Excelente artista");
        dto.setCalificacion(5);

        Usuario usuario = new Usuario();
        PerfilArtista artista = new PerfilArtista();

        when(usuarioRepository.findByIdUsuario(1)).thenReturn(usuario);
        when(perfilArtistaRepository.findByIdPerfilArtista(2)).thenReturn(artista);
        when(evaluacionRepository.findByUsuario_IdUsuarioAndPerfilArtista_IdPerfilArtista(1, 2)).thenReturn(null);

        String resultado = evaluacionService.EvaluarArtista(dto);

        assertEquals("Evaluacion registrada con exito.", resultado);
        verify(evaluacionRepository).save(any(Evaluacion.class));
    }

    @Test
    public void evaluarArtista_deberiaRetornarErrorSiUsuarioNoExiste() {
        EvaluacionArtistaDTO dto = new EvaluacionArtistaDTO();
        dto.setIdUsuario(99);
        dto.setIdArtista(1);

        when(usuarioRepository.findByIdUsuario(99)).thenReturn(null);

        String resultado = evaluacionService.EvaluarArtista(dto);

        assertEquals("Usuario no encontrado.", resultado);
        verifyNoInteractions(perfilArtistaRepository);
        verifyNoInteractions(evaluacionRepository);
    }

    @Test
    public void evaluarArtista_deberiaRetornarErrorSiArtistaNoExiste() {
        EvaluacionArtistaDTO dto = new EvaluacionArtistaDTO();
        dto.setIdUsuario(1);
        dto.setIdArtista(99);

        when(usuarioRepository.findByIdUsuario(1)).thenReturn(new Usuario());
        when(perfilArtistaRepository.findByIdPerfilArtista(99)).thenReturn(null);

        String resultado = evaluacionService.EvaluarArtista(dto);

        assertEquals("Artista no encontrado.", resultado);
        verifyNoInteractions(evaluacionRepository);
    }

    @Test
    public void evaluarArtista_deberiaDetectarEvaluacionExistente() {
        EvaluacionArtistaDTO dto = new EvaluacionArtistaDTO();
        dto.setIdUsuario(1);
        dto.setIdArtista(2);

        Usuario usuario = new Usuario();
        PerfilArtista artista = new PerfilArtista();
        Evaluacion evaluacion = new Evaluacion();

        when(usuarioRepository.findByIdUsuario(1)).thenReturn(usuario);
        when(perfilArtistaRepository.findByIdPerfilArtista(2)).thenReturn(artista);
        when(evaluacionRepository.findByUsuario_IdUsuarioAndPerfilArtista_IdPerfilArtista(1, 2)).thenReturn(evaluacion);

        String resultado = evaluacionService.EvaluarArtista(dto);

        assertEquals("Este usuario ya evaluo a este artista.", resultado);
        verify(evaluacionRepository, never()).save(any());
    }
}
