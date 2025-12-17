package com.henjid.henjid.service;

import com.henjid.henjid.model.Modulo;
import com.henjid.henjid.repository.ModuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuloService {

    @Autowired
    private ModuloRepository moduloRepository;

    // Obtener todos los módulos ordenados por el campo "orden"
    public List<Modulo> listarTodos() {
        return moduloRepository.findAllByOrderByOrdenAsc();
    }

    // Filtrar por nivel (A1, A2, Gramática, etc.)
    public List<Modulo> listarPorNivel(String nivel) {
        return moduloRepository.findByNivel(nivel);
    }

    // Obtener módulo por ID
    public Modulo obtenerPorId(Long id) {
        return moduloRepository.findById(id).orElse(null);
    }

    // Guardar módulo (para administración futura)
    public Modulo guardar(Modulo modulo) {
        return moduloRepository.save(modulo);
    }
}
