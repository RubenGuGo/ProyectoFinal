package es.cic.curso10.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.cic.curso10.backend.Model.TipoApuesta;
import es.cic.curso10.backend.Service.TipoApuestaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tipoapuestas")
public class TipoApuestaController {

    @Autowired
    private TipoApuestaService tipoApuestaService;

    @GetMapping
    public List<TipoApuesta> getAllTipoApuestas() {
        return tipoApuestaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoApuesta> getTipoApuestaById(@PathVariable Long id) {
        Optional<TipoApuesta> tipoApuesta = tipoApuestaService.findById(id);
        return tipoApuesta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public TipoApuesta createTipoApuesta(@RequestBody TipoApuesta tipoApuesta) {
        return tipoApuestaService.save(tipoApuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoApuesta> updateTipoApuesta(@PathVariable Long id, @RequestBody TipoApuesta tipoApuestaDetails) {
        Optional<TipoApuesta> tipoApuesta = tipoApuestaService.findById(id);
        if (tipoApuesta.isPresent()) {
            TipoApuesta tipoApuestaToUpdate = tipoApuesta.get();
            tipoApuestaToUpdate.setNombre(tipoApuestaDetails.getNombre());
            tipoApuestaToUpdate.setDescripcion(tipoApuestaDetails.getDescripcion());
            tipoApuestaToUpdate.setMaxima(tipoApuestaDetails.getMaxima());
            tipoApuestaToUpdate.setMinima(tipoApuestaDetails.getMinima());
            tipoApuestaToUpdate.setCombinada(tipoApuestaDetails.getCombinada());
            return ResponseEntity.ok(tipoApuestaService.save(tipoApuestaToUpdate));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoApuesta(@PathVariable Long id) {
        if (tipoApuestaService.findById(id).isPresent()) {
            tipoApuestaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}