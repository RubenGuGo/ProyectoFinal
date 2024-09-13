package es.cic.curso10.backend;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import es.cic.curso10.backend.Controller.TipoApuestaController;
import es.cic.curso10.backend.Model.Evento;
import es.cic.curso10.backend.Model.TipoApuesta;
import es.cic.curso10.backend.Repository.EventoRepository;
import es.cic.curso10.backend.Service.TipoApuestaService;

public class TipoApuestaControllerTestMock{

    @Mock
    private TipoApuestaService tipoApuestaService;

    @Mock
    private EventoRepository eventoRepository;

    @InjectMocks
    private TipoApuestaController tipoApuestaController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTipoApuestas() {
        TipoApuesta tipoApuesta1 = new TipoApuesta();
        TipoApuesta tipoApuesta2 = new TipoApuesta();
        List<TipoApuesta> tipoApuestaList = Arrays.asList(tipoApuesta1, tipoApuesta2);

        when(tipoApuestaService.findAll()).thenReturn(tipoApuestaList);

        List<TipoApuesta> result = tipoApuestaController.getAllTipoApuestas();

        assertEquals(2, result.size());
        verify(tipoApuestaService, times(1)).findAll();
    }

    @Test
    public void testGetTipoApuestaById() {
        TipoApuesta tipoApuesta = new TipoApuesta();
        when(tipoApuestaService.findById(anyLong())).thenReturn(Optional.of(tipoApuesta));

        ResponseEntity<TipoApuesta> response = tipoApuestaController.getTipoApuestaById(1L);

        assertEquals(ResponseEntity.ok(tipoApuesta), response);
        verify(tipoApuestaService, times(1)).findById(1L);
    }

    @Test
    public void testCreateTipoApuesta() {
        TipoApuesta tipoApuesta = new TipoApuesta();
        Evento evento = new Evento();
        evento.setId(1L);
        tipoApuesta.setEvento(evento);

        when(eventoRepository.findById(anyLong())).thenReturn(Optional.of(evento));
        when(tipoApuestaService.save(any(TipoApuesta.class))).thenReturn(tipoApuesta);

        ResponseEntity<TipoApuesta> response = tipoApuestaController.createTipoApuesta(tipoApuesta);

        assertEquals(ResponseEntity.ok(tipoApuesta), response);
        verify(eventoRepository, times(1)).findById(1L);
        verify(tipoApuestaService, times(1)).save(tipoApuesta);
    }

    @Test
    public void testUpdateTipoApuesta() {
        TipoApuesta tipoApuesta = new TipoApuesta();
        TipoApuesta tipoApuestaDetails = new TipoApuesta();
        tipoApuestaDetails.setNombre("Updated Name");

        when(tipoApuestaService.findById(anyLong())).thenReturn(Optional.of(tipoApuesta));
        when(tipoApuestaService.save(any(TipoApuesta.class))).thenReturn(tipoApuesta);

        ResponseEntity<TipoApuesta> response = tipoApuestaController.updateTipoApuesta(1L, tipoApuestaDetails);

        assertEquals(ResponseEntity.ok(tipoApuesta), response);
        verify(tipoApuestaService, times(1)).findById(1L);
        verify(tipoApuestaService, times(1)).save(tipoApuesta);
    }

    @Test
    public void testDeleteTipoApuesta() {
        when(tipoApuestaService.findById(anyLong())).thenReturn(Optional.of(new TipoApuesta()));

        ResponseEntity<Void> response = tipoApuestaController.deleteTipoApuesta(1L);

        assertEquals(ResponseEntity.noContent().build(), response);
        verify(tipoApuestaService, times(1)).findById(1L);
        verify(tipoApuestaService, times(1)).deleteById(1L);
    }
}