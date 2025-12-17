package com.henjid.henjid.model;

import jakarta.persistence.*;

@Entity
@Table(name = "leccion")
public class Leccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "modulo_id", nullable = false)
    private Modulo modulo;

    private String titulo;

    private String icono; // nombre archivo ícono (ej: "saludos.png")

    @Column(columnDefinition = "TEXT")
    private String contenido; // texto, json, html, etc.

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "imagen_url")
    private String imagenUrl;


    private Integer orden;

    public Leccion() {}

    // Getters y setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Modulo getModulo() { return modulo; }

    public void setModulo(Modulo modulo) { this.modulo = modulo; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getIcono() { return icono; }

    public void setIcono(String icono) { this.icono = icono; }

    public String getContenido() { return contenido; }

    public void setContenido(String contenido) { this.contenido = contenido; }

    public Integer getOrden() { return orden; }

    public void setOrden(Integer orden) { this.orden = orden; }

    public String getDescripcion() {
    return descripcion;
    }

    public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
    }

    public String getImagenUrl() {
    return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
    this.imagenUrl = imagenUrl;
    }

}
