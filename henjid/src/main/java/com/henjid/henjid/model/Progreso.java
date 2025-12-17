package com.henjid.henjid.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Progreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int porcentajeAvance;        // 0–100 %
    private int leccionesCompletadas;
    private int quizzesCompletados;
    private boolean completado;

    // ⚠ Antes era DATE → ahora dejamos este campo pero ya no lo usaremos para historial
    private LocalDate fechaUltimoAcceso = LocalDate.now();

    // ================================
    // 🎯 NUEVOS CAMPOS PARA HISTORIAL
    // ================================

    @Column(name = "ultima_actividad")
    private LocalDateTime ultimaActividad;   // Fecha + Hora

    @Column(name = "nota_quiz")
    private Integer notaQuiz;                // null si no ha hecho el quiz

    @Column(name = "tiempo_total_segundos")
    private Long tiempoTotalSegundos = 0L;   // Tiempo acumulado

    // ================================
    // RELACIONES
    // ================================

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "modulo_id")
    private Modulo modulo;

    public Progreso() {}

    // ==================================
    // GETTERS & SETTERS
    // ==================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPorcentajeAvance() {
        return porcentajeAvance;
    }

    public void setPorcentajeAvance(int porcentajeAvance) {
        this.porcentajeAvance = porcentajeAvance;
    }

    public int getLeccionesCompletadas() {
        return leccionesCompletadas;
    }

    public void setLeccionesCompletadas(int leccionesCompletadas) {
        this.leccionesCompletadas = leccionesCompletadas;
    }

    public int getQuizzesCompletados() {
        return quizzesCompletados;
    }

    public void setQuizzesCompletados(int quizzesCompletados) {
        this.quizzesCompletados = quizzesCompletados;
    }

    public LocalDate getFechaUltimoAcceso() {
        return fechaUltimoAcceso;
    }

    public void setFechaUltimoAcceso(LocalDate fechaUltimoAcceso) {
        this.fechaUltimoAcceso = fechaUltimoAcceso;
    }

    public LocalDateTime getUltimaActividad() {
        return ultimaActividad;
    }

    public void setUltimaActividad(LocalDateTime ultimaActividad) {
        this.ultimaActividad = ultimaActividad;
    }

    public Integer getNotaQuiz() {
        return notaQuiz;
    }

    public void setNotaQuiz(Integer notaQuiz) {
        this.notaQuiz = notaQuiz;
    }

    public Long getTiempoTotalSegundos() {
        return tiempoTotalSegundos;
    }

    public void setTiempoTotalSegundos(Long tiempoTotalSegundos) {
        this.tiempoTotalSegundos = tiempoTotalSegundos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }
}
