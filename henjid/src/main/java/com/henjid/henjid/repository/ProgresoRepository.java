package com.henjid.henjid.repository;

import java.util.List;
import java.util.Optional;

import com.henjid.henjid.model.Progreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgresoRepository extends JpaRepository<Progreso, Long> {

    // Obtener progreso de un usuario en un módulo específico - versión correcta
    Optional<Progreso> findByUsuarioIdAndModuloId(Long usuarioId, Long moduloId);

    // Obtener todos los progresos de un usuario
    List<Progreso> findByUsuarioId(Long usuarioId);

    // ============================================
    // 🔥 MÉTODOS OPCIONALES PARA OPERACIONES DIRECTAS
    // ============================================

    // Actualizar última actividad (timestamp)
    @Modifying
    @Query("UPDATE Progreso p SET p.ultimaActividad = CURRENT_TIMESTAMP WHERE p.usuario.id = :usuarioId AND p.modulo.id = :moduloId")
    void actualizarUltimaActividad(Long usuarioId, Long moduloId);

    // Guardar nota del quiz
    @Modifying
    @Query("UPDATE Progreso p SET p.notaQuiz = :nota WHERE p.usuario.id = :usuarioId AND p.modulo.id = :moduloId")
    void actualizarNotaQuiz(Long usuarioId, Long moduloId, Integer nota);

    // Sumar tiempo acumulado
    @Modifying
    @Query("UPDATE Progreso p SET p.tiempoTotalSegundos = p.tiempoTotalSegundos + :segundos WHERE p.usuario.id = :usuarioId AND p.modulo.id = :moduloId")
    void agregarTiempo(Long usuarioId, Long moduloId, Long segundos);
}
