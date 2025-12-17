package com.henjid.henjid.repository;

import com.henjid.henjid.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar por correo
    Usuario findByCorreo(String correo);

    // Buscar por username
    Usuario findByUsername(String username);

    // Verificar si ya existe un usuario
    boolean existsByCorreo(String correo);
    boolean existsByUsername(String username);
}
