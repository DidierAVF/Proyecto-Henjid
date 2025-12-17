package com.henjid.henjid.model;

import jakarta.persistence.*;

@Entity
@Table(name = "progreso_leccion")
public class ProgresoLeccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Usuario que hace la lección
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Lección a la que pertenece el progreso
    @ManyToOne
    @JoinColumn(name = "leccion_id", nullable = false)
    private Leccion leccion;

    // ¿La terminó o no?
    private boolean completada;

    public ProgresoLeccion() {}

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Leccion getLeccion() {
        return leccion;
    }

    public void setLeccion(Leccion leccion) {
        this.leccion = leccion;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }
}
