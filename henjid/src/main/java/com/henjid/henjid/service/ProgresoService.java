package com.henjid.henjid.service;

import com.henjid.henjid.model.Modulo;
import com.henjid.henjid.model.Progreso;
import com.henjid.henjid.model.Usuario;
import com.henjid.henjid.repository.ProgresoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProgresoService {

    private final ProgresoRepository progresoRepository;

    public ProgresoService(ProgresoRepository progresoRepository) {
        this.progresoRepository = progresoRepository;
    }

    // ============================================
    // 🧩 OBTENER O CREAR PROGRESO
    // ============================================
    public Progreso obtenerOcrear(Usuario usuario, Modulo modulo) {
        return progresoRepository
                .findByUsuarioIdAndModuloId(usuario.getId(), modulo.getId())
                .orElseGet(() -> crearProgresoInicial(usuario, modulo));
    }

    // ============================================
    // Crear progreso cuando no existe
    // ============================================
    public Progreso crearProgresoInicial(Usuario usuario, Modulo modulo) {
        Progreso p = new Progreso();
        p.setUsuario(usuario);
        p.setModulo(modulo);
        p.setPorcentajeAvance(0);
        p.setLeccionesCompletadas(0);
        p.setQuizzesCompletados(0);
        p.setCompletado(false);
        p.setUltimaActividad(LocalDateTime.now());
        return progresoRepository.save(p);
    }

    // ============================================
    // 🚀 GUARDAR ACTIVIDAD (para Historial)
    // ============================================
    @Transactional
    public void registrarActividad(Long usuarioId, Long moduloId) {
        progresoRepository.actualizarUltimaActividad(usuarioId, moduloId);
    }

    // ============================================
    // 🕒 SUMAR TIEMPO DE ESTUDIO (segundos)
    // ============================================
    @Transactional
    public void agregarTiempo(Long usuarioId, Long moduloId, long segundos) {
        progresoRepository.agregarTiempo(usuarioId, moduloId, segundos);
    }

    // ============================================
    // 📝 GUARDAR NOTA DEL QUIZ
    // ============================================
    @Transactional
    public void guardarNotaQuiz(Long usuarioId, Long moduloId, int nota) {
        progresoRepository.actualizarNotaQuiz(usuarioId, moduloId, nota);
    }

    // ============================================
    // 🔥 ACTUALIZAR PROGRESO POR LECCIÓN
    // ============================================
    public void actualizarProgresoLeccion(Usuario usuario, Modulo modulo) {

        Progreso progreso = obtenerOcrear(usuario, modulo);

        // Sumar 1 lección
        progreso.setLeccionesCompletadas(progreso.getLeccionesCompletadas() + 1);

        // A1 tiene 6 lecciones = 75 %
        double porcentaje = (progreso.getLeccionesCompletadas() * (75.0 / 6.0))
                + (progreso.getQuizzesCompletados() * 25.0);

        progreso.setPorcentajeAvance((int) porcentaje);

        if (porcentaje >= 100) {
            progreso.setCompletado(true);
        }

        progresoRepository.save(progreso);
    }

    // ============================================
    // 🔥 ACTUALIZAR PROGRESO POR QUIZ
    // ============================================
    public void actualizarProgresoQuiz(Usuario usuario, Modulo modulo, int notaQuiz) {

        Progreso progreso = obtenerOcrear(usuario, modulo);

        progreso.setQuizzesCompletados(1);
        progreso.setNotaQuiz(notaQuiz);

        double porcentaje = (progreso.getLeccionesCompletadas() * (75.0 / 6.0))
                + 25.0;

        progreso.setPorcentajeAvance((int) porcentaje);

        if (porcentaje >= 100) {
            progreso.setCompletado(true);
        }

        progresoRepository.save(progreso);
    }

    // ============================================
    // Obtener progresos del usuario
    // ============================================
    public List<Progreso> obtenerPorUsuario(Usuario usuario) {
        // Como findByUsuario(usuario) NO existe, usamos el ID
        return progresoRepository.findByUsuarioId(usuario.getId());
    }

    // ============================================
    // ✔ Saber si un módulo está completado
    // ============================================
    public boolean estaCompletado(Usuario usuario, Modulo modulo) {
        return progresoRepository
                .findByUsuarioIdAndModuloId(usuario.getId(), modulo.getId())
                .map(Progreso::isCompletado)
                .orElse(false);
    }


    public void marcarCompletado(Usuario usuario, Modulo modulo) {

    Progreso progreso = obtenerOcrear(usuario, modulo);

    progreso.setCompletado(true);
    progreso.setPorcentajeAvance(100);

    // Si quieres, marcamos el quiz como completado también:
    progreso.setQuizzesCompletados(1);

    progresoRepository.save(progreso);
}


public void guardarNotaQuiz(Usuario usuario, Modulo modulo, int nota) {

    Progreso progreso = progresoRepository
            .findByUsuarioIdAndModuloId(usuario.getId(), modulo.getId())
            .orElseGet(() -> crearProgresoInicial(usuario, modulo));

    progreso.setNotaQuiz(nota);
    progreso.setQuizzesCompletados(1);

    // Si ya completó las 6 lecciones, el quiz otorga 25%
    double porcentaje = (progreso.getLeccionesCompletadas() * (75.0 / 6.0))
                        + (nota >= 6 ? 25.0 : 0);

    progreso.setPorcentajeAvance((int) porcentaje);

    if (porcentaje >= 100) {
        progreso.setCompletado(true);
    }

    progresoRepository.save(progreso);
}


}
