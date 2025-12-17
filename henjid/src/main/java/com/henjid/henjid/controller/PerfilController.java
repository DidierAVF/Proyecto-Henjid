package com.henjid.henjid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

import java.util.Map;

@Controller
public class PerfilController {

    private static final Map<String, String> PAISES = Map.of(
            "VE", "Venezuela",
            "CO", "Colombia",
            "MX", "México",
            "AR", "Argentina",
            "CL", "Chile",
            "PE", "Perú",
            "EC", "Ecuador",
            "US", "Estados Unidos",
            "ES", "España"
    );

    @GetMapping("/perfil")
    public String perfil(HttpSession session, Model model) {

        if (session.getAttribute("usuarioId") == null) {
            return "redirect:/login";
        }

        String codigoPais = (String) session.getAttribute("pais");
        String paisCompleto = PAISES.getOrDefault(codigoPais, codigoPais);

        model.addAttribute("nombre", session.getAttribute("nombre"));
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("correo", session.getAttribute("correo")); // <- nuevo dato
        model.addAttribute("pais", paisCompleto);
        model.addAttribute("foto", session.getAttribute("foto"));
   

        return "perfil";
    }
}
