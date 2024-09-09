package es.cic.curso10.backend.Model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String nombre;

    @Column(length = 250)
    private String descripcion;

    @Column(length = 100)
    private String pais;

    @Column(length = 100)
    private String ciudad;

    @Column(length = 100)
    private String direccion;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Temporal(TemporalType.TIME)
    private Date horaInicio;

    @Temporal(TemporalType.TIME)
    private Date horaFinal;

    @OneToMany
    private List<TipoApuesta> tiposDeApuestas;

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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Date horaFinal) {
        this.horaFinal = horaFinal;
    }

    public List<TipoApuesta> getTiposDeApuestas() {
        return tiposDeApuestas;
    }

    public void setTiposDeApuestas(List<TipoApuesta> tiposDeApuestas) {
        this.tiposDeApuestas = tiposDeApuestas;
    }

    @PrePersist
    @PreUpdate
    private void validateHoraInicio() {
        if (horaInicio != null && horaFinal != null && horaInicio.after(horaFinal)) {
            throw new IllegalArgumentException("La hora de inicio no puede ser después de la hora final");
        }
    }
}