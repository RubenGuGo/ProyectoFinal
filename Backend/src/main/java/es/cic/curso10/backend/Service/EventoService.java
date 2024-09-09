package es.cic.curso10.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.cic.curso10.backend.Model.Evento;
import es.cic.curso10.backend.Repository.EventoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public List<Evento> findAll() {
        return eventoRepository.findAll();
    }

    public Optional<Evento> findById(Long id) {
        return eventoRepository.findById(id);
    }

    public Evento save(Evento evento) {
        return eventoRepository.save(evento);
    }

    public void deleteById(Long id) {
        eventoRepository.deleteById(id);
    }
}