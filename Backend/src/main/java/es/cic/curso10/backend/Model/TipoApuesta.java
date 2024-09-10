package es.cic.curso10.backend.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class TipoApuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    @NotNull
    private String nombre;

    @Column(length = 250)
    private String descripcion;

    @Min(0)
    private Long maxima;

    @Min(0)
    private Long minima;

    private Boolean combinada;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Evento getEvento() {
        return evento;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getMaxima() {
        return maxima;
    }

    public void setMaxima(Long maxima) {
        this.maxima = maxima;
    }

    public Long getMinima() {
        return minima;
    }

    public void setMinima(Long minima) {
        this.minima = minima;
    }

    public Boolean getCombinada() {
        return combinada;
    }

    public void setCombinada(Boolean combinada) {
        this.combinada = combinada;
    }
}