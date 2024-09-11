package es.cic.curso10.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.cic.curso10.backend.Model.TipoApuesta;

@Repository
public interface TipoApuestaRepository extends JpaRepository<TipoApuesta, Long> {
}