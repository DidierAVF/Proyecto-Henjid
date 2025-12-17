package com.henjid.henjid.service;

import com.henjid.henjid.model.Leccion;
import com.henjid.henjid.model.ProgresoLeccion;
import com.henjid.henjid.model.Usuario;
import com.henjid.henjid.repository.ProgresoLeccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgresoLeccionService {

    private final ProgresoLeccionRepository repo;

    public ProgresoLeccionService(ProgresoLeccionRepository repo) {
        this.repo = repo;
    }

    // Verificar si una lección está completada
    public boolean estaCompletada(Usuario usuario, Leccion leccion) {
        return repo.findByUsuarioIdAndLeccionId(usuario.getId(), leccion.getId())
                .map(ProgresoLeccion::isCompletada)
                .orElse(false);
    }

    // ============================================
    // 🔥 MARCAR COMO COMPLETADA Y SABER SI ES NUEVA
    // ============================================
    public boolean marcarCompletada(Usuario usuario, Leccion leccion) {

        Optional<ProgresoLeccion> existing =
                repo.findByUsuarioIdAndLeccionId(usuario.getId(), leccion.getId());

        // Si ya existía y está completada → no es nueva
        if (existing.isPresent() && existing.get().isCompletada()) {
            return false; // NO SUMAR PROGRESO
        }

        // Caso contrario, crear o actualizar registro
        ProgresoLeccion progreso = existing.orElse(new ProgresoLeccion());
        progreso.setUsuario(usuario);
        progreso.setLeccion(leccion);
        progreso.setCompletada(true);

        repo.save(progreso);
        return true; // Se completó por primera vez → SUMAR PROGRESO
    }

    // Obtener todas las completadas por usuario
    public List<ProgresoLeccion> obtenerCompletadasPorUsuario(Usuario usuario) {
        return repo.findByUsuarioId(usuario.getId());
    }
}
