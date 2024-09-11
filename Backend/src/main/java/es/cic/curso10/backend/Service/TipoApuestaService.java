package es.cic.curso10.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.cic.curso10.backend.Model.TipoApuesta;
import es.cic.curso10.backend.Repository.TipoApuestaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TipoApuestaService {

    @Autowired
    private TipoApuestaRepository tipoApuestaRepository;

    public List<TipoApuesta> findAll() {
        return tipoApuestaRepository.findAll();
    }

    public Optional<TipoApuesta> findById(Long id) {
        return tipoApuestaRepository.findById(id);
    }

    public TipoApuesta save(TipoApuesta tipoApuesta) {
        return tipoApuestaRepository.save(tipoApuesta);
    }

    public void deleteById(Long id) {
        tipoApuestaRepository.deleteById(id);
    }
}