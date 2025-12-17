package com.henjid.henjid.service;

import com.henjid.henjid.model.Actividad;
import com.henjid.henjid.repository.ActividadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActividadService {

    private final ActividadRepository repo;

    public ActividadService(ActividadRepository repo) {
        this.repo = repo;
    }

    public List<Actividad> getActividadesByModulo(Long moduloId) {
        return repo.findByModuloId(moduloId);
    }
}
