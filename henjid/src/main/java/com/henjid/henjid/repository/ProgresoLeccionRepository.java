package com.henjid.henjid.repository;

import com.henjid.henjid.model.ProgresoLeccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgresoLeccionRepository extends JpaRepository<ProgresoLeccion, Long> {

    // Buscar si una lección ya fue completada por un usuario
    Optional<ProgresoLeccion> findByUsuarioIdAndLeccionId(Long usuarioId, Long leccionId);

    // Obtener todas las lecciones completadas por un usuario
    List<ProgresoLeccion> findByUsuarioId(Long usuarioId);
}
