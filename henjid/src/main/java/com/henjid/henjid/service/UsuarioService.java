package com.henjid.henjid.service;

import com.henjid.henjid.model.Usuario;
import com.henjid.henjid.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // REGISTRAR
    public Usuario registrar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // LOGIN
    public Usuario login(String identificador, String password) {

        Usuario usuario = usuarioRepository.findByCorreo(identificador);

        if (usuario == null) {
            usuario = usuarioRepository.findByUsername(identificador);
        }

        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario;
        }

        return null;
    }

    // EXISTE CORREO
    public boolean existeCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    // EXISTE USERNAME
    public boolean existeUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    // ACTUALIZAR USUARIO
    public Usuario actualizar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // BUSCAR POR ID
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
    // BUSCAR POR USERNAME
    public Usuario buscarPorUsername(String username) {
    return usuarioRepository.findByUsername(username);
    }

}
