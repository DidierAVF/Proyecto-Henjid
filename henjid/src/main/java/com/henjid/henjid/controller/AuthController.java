package com.henjid.henjid.controller;

import com.henjid.henjid.model.Usuario;
import com.henjid.henjid.service.UsuarioService;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    // LOGIN GET
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    // LOGIN POST
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String identificador,
                                @RequestParam String password,
                                Model model,
                                HttpSession session) {

        Usuario usuario = usuarioService.login(identificador, password);

        if (usuario == null) {
            model.addAttribute("error", "Credenciales incorrectas");
            return "login";
        }

        // Guardar datos en sesión
        session.setAttribute("usuarioId", usuario.getId());
        session.setAttribute("username", usuario.getUsername());
        session.setAttribute("nombre", usuario.getNombreCompleto());
        session.setAttribute("pais", usuario.getPais());
        session.setAttribute("foto", usuario.getFotoPerfil()); // por ahora será null
        session.setAttribute("correo", usuario.getCorreo());

        // Volver a la página principal
        return "redirect:/";
    }

    // REGISTRO GET
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    // REGISTRO POST
    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute Usuario usuario,
                                   Model model) {

        if (usuarioService.existeCorreo(usuario.getCorreo())) {
            model.addAttribute("error", "El correo ya está registrado");
            return "registro";
        }

        if (usuarioService.existeUsername(usuario.getUsername())) {
            model.addAttribute("error", "El nombre de usuario ya está en uso");
            return "registro";
        }

        usuarioService.registrar(usuario);
        return "redirect:/login";
    }

    // RECUPERAR
    @GetMapping("/recuperar")
    public String mostrarRecuperar() {
        return "recuperar";
    }

    // LOGOUT
   // LOGOUT
@GetMapping("/logout")
public String logout(HttpSession session) {

    String username = (String) session.getAttribute("username");

    if (username != null) {
        Usuario usuario = usuarioService.buscarPorUsername(username);

        if (usuario != null) {
            usuario.setUltimaActividad(LocalDateTime.now());
            usuarioService.registrar(usuario);
        }
    }

    session.invalidate(); // cerrar sesión

    return "redirect:/";
}

}
