package es.cic.curso10.backend;

import es.cic.curso10.backend.Model.Evento;
import es.cic.curso10.backend.Model.TipoApuesta;
import es.cic.curso10.backend.Repository.EventoRepository;
import es.cic.curso10.backend.Repository.TipoApuestaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TipoApuestaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TipoApuestaRepository tipoApuestaRepository;

    @Autowired
    private EventoRepository eventoRepository;

    private Evento evento;

    @BeforeEach
    public void setup() {
        tipoApuestaRepository.deleteAll();
        eventoRepository.deleteAll();

        evento = new Evento();
        evento.setNombre("Evento Test");
        evento = eventoRepository.save(evento);
    }

    @Test
    public void testCreateTipoApuesta() throws Exception {
        mockMvc.perform(post("/api/tipoapuestas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Tipo Apuesta Test\",\"descripcion\":\"Descripción Test\",\"maxima\":100,\"minima\":10,\"combinada\":true,\"evento\":{\"id\":" + evento.getId() + "}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Tipo Apuesta Test"));
    }

    @Test
    public void testGetAllTipoApuestas() throws Exception {
        TipoApuesta tipoApuesta = new TipoApuesta();
        tipoApuesta.setNombre("Tipo Apuesta Test");
        tipoApuesta.setEvento(evento);
        tipoApuestaRepository.save(tipoApuesta);

        mockMvc.perform(get("/api/tipoapuestas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Tipo Apuesta Test"));
    }

    @Test
    public void testGetTipoApuestaById() throws Exception {
        TipoApuesta tipoApuesta = new TipoApuesta();
        tipoApuesta.setNombre("Tipo Apuesta Test");
        tipoApuesta.setEvento(evento);
        tipoApuesta = tipoApuestaRepository.save(tipoApuesta);

        mockMvc.perform(get("/api/tipoapuestas/" + tipoApuesta.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Tipo Apuesta Test"));
    }

    @Test
    public void testUpdateTipoApuesta() throws Exception {
        TipoApuesta tipoApuesta = new TipoApuesta();
        tipoApuesta.setNombre("Tipo Apuesta Test");
        tipoApuesta.setEvento(evento);
        tipoApuesta = tipoApuestaRepository.save(tipoApuesta);

        mockMvc.perform(put("/api/tipoapuestas/" + tipoApuesta.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Tipo Apuesta Test Actualizado\",\"descripcion\":\"Descripción Test\",\"maxima\":100,\"minima\":10,\"combinada\":true,\"evento\":{\"id\":" + evento.getId() + "}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Tipo Apuesta Test Actualizado"));
    }

    @Test
    public void testDeleteTipoApuesta() throws Exception {
        TipoApuesta tipoApuesta = new TipoApuesta();
        tipoApuesta.setNombre("Tipo Apuesta Test");
        tipoApuesta.setEvento(evento);
        tipoApuesta = tipoApuestaRepository.save(tipoApuesta);

        mockMvc.perform(delete("/api/tipoapuestas/" + tipoApuesta.getId()))
                .andExpect(status().isNoContent());
    }
}