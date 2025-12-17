package com.henjid.henjid.model;

import jakarta.persistence.*;

@Entity
@Table(name = "actividad")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "modulo_id", nullable = false)
    private Modulo modulo;

    private String titulo;   // Ej: "Crucigrama"
    private String tipo;     // Ej: "crucigrama", "ahorcado"

    @Column(columnDefinition = "TEXT")
    private String datos;    // JSON con preguntas, palabras, etc.

    public Actividad() {}

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Modulo getModulo() { return modulo; }

    public void setModulo(Modulo modulo) { this.modulo = modulo; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDatos() { return datos; }

    public void setDatos(String datos) { this.datos = datos; }
}
