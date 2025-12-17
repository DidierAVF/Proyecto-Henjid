package com.henjid.henjid.repository;

import com.henjid.henjid.model.Leccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeccionRepository extends JpaRepository<Leccion, Long> {
    List<Leccion> findByModuloIdOrderByOrdenAsc(Long moduloId);
}
