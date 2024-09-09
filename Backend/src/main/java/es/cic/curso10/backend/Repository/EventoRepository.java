package es.cic.curso10.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.cic.curso10.backend.Model.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
}