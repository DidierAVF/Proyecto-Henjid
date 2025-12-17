package com.henjid.henjid.controller;

import com.henjid.henjid.model.Usuario;
import com.henjid.henjid.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Controller
public class ConfiguracionController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/configuracion")
    public String mostrarConfiguracion(HttpSession session, Model model) {

        if (session.getAttribute("usuarioId") == null) {
            return "redirect:/login";
        }

        model.addAttribute("nombre", session.getAttribute("nombre"));
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("correo", session.getAttribute("correo"));
        model.addAttribute("pais", session.getAttribute("pais"));
        model.addAttribute("foto", session.getAttribute("foto"));

        return "configuracion";
    }

    @PostMapping("/configuracion")
    public String guardarCambios(
            @RequestParam String nombre,
            @RequestParam String pais,
            @RequestParam(required = false) MultipartFile foto,
            HttpSession session
    ) throws Exception {

        Long id = (Long) session.getAttribute("usuarioId");
        Usuario usuario = usuarioService.buscarPorId(id);

        usuario.setNombreCompleto(nombre);
        usuario.setPais(pais);

        // ------- GUARDAR FOTO -------
        if (foto != null && !foto.isEmpty()) {

            String nombreArchivo = System.currentTimeMillis() + "_" + foto.getOriginalFilename();

            // Carpeta REAL donde se guardan las fotos
            String ruta = System.getProperty("user.dir") + "/uploads/";

            File carpeta = new File(ruta);
            if (!carpeta.exists()) carpeta.mkdirs();

            File destino = new File(ruta + nombreArchivo);
            foto.transferTo(destino);

            usuario.setFotoPerfil(nombreArchivo);
            session.setAttribute("foto", nombreArchivo);
        }

        usuarioService.actualizar(usuario);

        // Actualizar sesión
        session.setAttribute("nombre", nombre);
        session.setAttribute("pais", pais);

        return "redirect:/perfil";
    }
}
