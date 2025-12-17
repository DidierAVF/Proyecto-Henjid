package com.henjid.henjid.model;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private String nivel;     // A1, A2, B1, etc.
    private int orden;
    private String color;

    // Evita que Thymeleaf/Hibernate intente procesar esta colección
    @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Progreso> progresos;

    public Modulo() {}

    // ------------ GETTERS & SETTERS ------------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public int getOrden() { return orden; }
    public void setOrden(int orden) { this.orden = orden; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public List<Progreso> getProgresos() { return progresos; }
    public void setProgresos(List<Progreso> progresos) { this.progresos = progresos; }
}
