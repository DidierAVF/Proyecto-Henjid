package com.henjid.henjid.repository;

import com.henjid.henjid.model.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {
    List<Actividad> findByModuloId(Long moduloId);
}
