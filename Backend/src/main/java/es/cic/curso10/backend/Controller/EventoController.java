package es.cic.curso10.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.cic.curso10.backend.Model.Evento;
import es.cic.curso10.backend.Service.EventoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public List<Evento> getAllEventos() {
        return eventoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> getEventoById(@PathVariable Long id) {
        Optional<Evento> evento = eventoService.findById(id);
        return evento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Evento createEvento(@RequestBody Evento evento) {
        return eventoService.save(evento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> updateEvento(@PathVariable Long id, @RequestBody Evento eventoDetails) {
        Optional<Evento> evento = eventoService.findById(id);
        if (evento.isPresent()) {
            Evento eventoToUpdate = evento.get();
            eventoToUpdate.setNombre(eventoDetails.getNombre());
            eventoToUpdate.setDescripcion(eventoDetails.getDescripcion());
            eventoToUpdate.setPais(eventoDetails.getPais());
            eventoToUpdate.setCiudad(eventoDetails.getCiudad());
            eventoToUpdate.setDireccion(eventoDetails.getDireccion());
            eventoToUpdate.setFecha(eventoDetails.getFecha());
            eventoToUpdate.setHoraInicio(eventoDetails.getHoraInicio());
            eventoToUpdate.setHoraFinal(eventoDetails.getHoraFinal());
            eventoToUpdate.setTiposDeApuestas(eventoDetails.getTiposDeApuestas());
            return ResponseEntity.ok(eventoService.save(eventoToUpdate));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable Long id) {
        if (eventoService.findById(id).isPresent()) {
            eventoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}