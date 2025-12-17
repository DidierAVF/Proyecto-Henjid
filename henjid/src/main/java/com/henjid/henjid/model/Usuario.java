package com.henjid.henjid.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Datos principales
    private String nombreCompleto;
    private String correo;
    private String username;
    private String password;
    private String pais;

    // Foto de perfil (ruta del archivo)
    private String fotoPerfil;

    // Estado y auditoría
    private Boolean activo = true;
    private LocalDate fechaRegistro = LocalDate.now();

    // Relación con progreso
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Progreso> progresos;

    public Usuario() {}

    // ===== GETTERS & SETTERS =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public String getFotoPerfil() { return fotoPerfil; }
    public void setFotoPerfil(String fotoPerfil) { this.fotoPerfil = fotoPerfil; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public List<Progreso> getProgresos() { return progresos; }
    public void setProgresos(List<Progreso> progresos) { this.progresos = progresos; }


    private LocalDateTime ultimaActividad;

public LocalDateTime getUltimaActividad() { return ultimaActividad; }
public void setUltimaActividad(LocalDateTime ultimaActividad) {
    this.ultimaActividad = ultimaActividad;
}

}
