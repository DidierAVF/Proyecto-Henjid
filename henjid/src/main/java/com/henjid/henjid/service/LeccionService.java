package com.henjid.henjid.service;

import com.henjid.henjid.model.Leccion;
import com.henjid.henjid.repository.LeccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeccionService {

    private final LeccionRepository repo;

    public LeccionService(LeccionRepository repo) {
        this.repo = repo;
    }

    // Obtener una lección por ID
    public Leccion obtenerPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Lección no encontrada con ID: " + id));
    }

    // Obtener lecciones de un módulo por orden ASC
    public List<Leccion> getLeccionesByModulo(Long moduloId) {
        return repo.findByModuloIdOrderByOrdenAsc(moduloId);
    }

    // Obtener ID de la lección anterior
    public Long buscarAnteriorId(Long actualId, Long moduloId) {
        List<Leccion> lecciones = getLeccionesByModulo(moduloId);

        for (int i = 0; i < lecciones.size(); i++) {
            if (lecciones.get(i).getId().equals(actualId)) {

                // Si es la primera, no hay anterior
                if (i == 0) {
                    return null;
                }

                return lecciones.get(i - 1).getId();
            }
        }

        return null;
    }

    // Obtener ID de la siguiente lección
    public Long buscarSiguienteId(Long actualId, Long moduloId) {
        List<Leccion> lecciones = getLeccionesByModulo(moduloId);

        for (int i = 0; i < lecciones.size(); i++) {
            if (lecciones.get(i).getId().equals(actualId)) {

                // Si es la última, no hay siguiente
                if (i == lecciones.size() - 1) {
                    return null;
                }

                return lecciones.get(i + 1).getId();
            }
        }

        return null;
    }
}
