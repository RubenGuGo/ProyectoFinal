package es.cic.curso10.backend;

import es.cic.curso10.backend.Model.Evento;
import es.cic.curso10.backend.Repository.EventoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EventoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventoRepository eventoRepository;

    @BeforeEach
    public void setup() {
        eventoRepository.deleteAll();
    }

    @Test
    public void testCreateEvento() throws Exception {
        Evento evento = new Evento();
        evento.setNombre("Evento Test");
        evento.setDescripcion("Descripción Test");
        evento.setPais("España");
        evento.setCiudad("Madrid");
        evento.setDireccion("Calle Test");
        evento.setFecha(LocalDate.now());
        evento.setHoraInicio(LocalTime.now());
        evento.setHoraFinal(LocalTime.now());

        mockMvc.perform(post("/api/eventos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Evento Test\",\"descripcion\":\"Descripción Test\",\"pais\":\"España\",\"ciudad\":\"Madrid\",\"direccion\":\"Calle Test\",\"fecha\":\"2023-01-01\",\"horaInicio\":\"12:00:00\",\"horaFinal\":\"14:00:00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Evento Test"));
    }

    @Test
    public void testGetAllEventos() throws Exception {
        Evento evento = new Evento();
        evento.setNombre("Evento Test");
        eventoRepository.save(evento);

        mockMvc.perform(get("/api/eventos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Evento Test"));
    }

    @Test
    public void testGetEventoById() throws Exception {
        Evento evento = new Evento();
        evento.setNombre("Evento Test");
        evento = eventoRepository.save(evento);

        mockMvc.perform(get("/api/eventos/" + evento.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Evento Test"));
    }

    @Test
    public void testUpdateEvento() throws Exception {
        Evento evento = new Evento();
        evento.setNombre("Evento Test");
        evento = eventoRepository.save(evento);

        mockMvc.perform(put("/api/eventos/" + evento.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Evento Test Actualizado\",\"descripcion\":\"Descripción Test\",\"pais\":\"España\",\"ciudad\":\"Madrid\",\"direccion\":\"Calle Test\",\"fecha\":\"2023-01-01\",\"horaInicio\":\"12:00:00\",\"horaFinal\":\"14:00:00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Evento Test Actualizado"));
    }

    @Test
    public void testDeleteEvento() throws Exception {
        Evento evento = new Evento();
        evento.setNombre("Evento Test");
        evento = eventoRepository.save(evento);

        mockMvc.perform(delete("/api/eventos/" + evento.getId()))
                .andExpect(status().isNoContent());
    }
}