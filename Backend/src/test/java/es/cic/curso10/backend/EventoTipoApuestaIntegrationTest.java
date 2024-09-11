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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EventoTipoApuestaIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private TipoApuestaRepository tipoApuestaRepository;

    @BeforeEach
    public void setup() {
        tipoApuestaRepository.deleteAll();
        eventoRepository.deleteAll();
    }

    @Test
    public void testEventoTipoApuestaRelationship() throws Exception {
        // Crear un evento
        Evento evento = new Evento();
        evento.setNombre("Evento Test");
        evento.setDescripcion("Descripción Test");
        evento.setPais("España");
        evento.setCiudad("Madrid");
        evento.setDireccion("Calle Test");
        evento.setFecha(LocalDate.now());
        evento.setHoraInicio(LocalTime.now());
        evento.setHoraFinal(LocalTime.now());
        evento = eventoRepository.save(evento);

        // Crear un tipo de apuesta y asociarlo al evento
        TipoApuesta tipoApuesta = new TipoApuesta();
        tipoApuesta.setNombre("Tipo Apuesta Test");
        tipoApuesta.setDescripcion("Descripción Test");
        tipoApuesta.setMaxima(100l);
        tipoApuesta.setMinima(10l);
        tipoApuesta.setCombinada(true);
        tipoApuesta.setEvento(evento);
        tipoApuesta = tipoApuestaRepository.save(tipoApuesta);

        // Verificar la relación
        mockMvc.perform(get("/api/eventos/" + evento.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.tiposDeApuestas[0].nombre").value("Tipo Apuesta Test"));

        List<TipoApuesta> tiposDeApuestas = tipoApuestaRepository.findAll();
        assertThat(tiposDeApuestas).hasSize(1);
        assertThat(tiposDeApuestas.get(0).getEvento().getId()).isEqualTo(evento.getId());
    }
}