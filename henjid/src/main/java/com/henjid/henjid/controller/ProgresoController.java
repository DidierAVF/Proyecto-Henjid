package com.henjid.henjid.controller;

import com.henjid.henjid.model.Modulo;
import com.henjid.henjid.model.Progreso;
import com.henjid.henjid.model.Usuario;
import com.henjid.henjid.service.ModuloService;
import com.henjid.henjid.service.ProgresoService;
import com.henjid.henjid.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class ProgresoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProgresoService progresoService;

    @Autowired
    private ModuloService moduloService;

    @GetMapping("/progreso")
    public String mostrarProgreso(HttpSession session, Model model) {

        // 1. Validar sesión
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioService.buscarPorUsername(username);

        // 2. Obtener todos los módulos
        List<Modulo> modulos = moduloService.listarTodos();

        // 3. Obtener progresos que ya existen
        List<Progreso> progresosExistentes = progresoService.obtenerPorUsuario(usuario);

        // Convertir lista existente en un mapa para acceso rápido
        Map<Long, Progreso> mapaProgresos = new HashMap<>();
        for (Progreso p : progresosExistentes) {
            mapaProgresos.put(p.getModulo().getId(), p);
        }

        // 4. Crear lista final sincronizada
        List<Progreso> progresosFinal = new ArrayList<>();

        for (Modulo m : modulos) {

            Progreso progreso = mapaProgresos.get(m.getId());

            // Si no existe progreso -> crear uno inicial
            if (progreso == null) {
                progreso = progresoService.crearProgresoInicial(usuario, m);
            }

            progresosFinal.add(progreso);
        }

        // 5. Calcular progreso global correctamente
        double totalPorcentaje = progresosFinal.stream()
                .mapToDouble(Progreso::getPorcentajeAvance)
                .sum();

        double progresoGlobal = modulos.isEmpty()
                ? 0
                : totalPorcentaje / modulos.size();

        // 6. Enviar datos al HTML
        model.addAttribute("usuario", usuario);
        model.addAttribute("modulos", modulos);
        model.addAttribute("progresos", progresosFinal);
        model.addAttribute("progresoGlobal", progresoGlobal);

        return "progreso";
    }
}
