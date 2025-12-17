package com.henjid.henjid.repository;

import com.henjid.henjid.model.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Long> {

    List<Modulo> findByNivel(String nivel);

    // Nuevo método para ordenar por "orden"
    List<Modulo> findAllByOrderByOrdenAsc();
}


