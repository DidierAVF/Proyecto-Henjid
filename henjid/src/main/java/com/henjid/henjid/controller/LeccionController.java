package com.henjid.henjid.controller;

import com.henjid.henjid.model.Leccion;
import com.henjid.henjid.model.Modulo;
import com.henjid.henjid.model.Usuario;
import com.henjid.henjid.service.LeccionService;
import com.henjid.henjid.service.ProgresoLeccionService;
import com.henjid.henjid.service.ProgresoService;
import com.henjid.henjid.service.UsuarioService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/leccion")
public class LeccionController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LeccionService leccionService;

    @Autowired
    private ProgresoLeccionService progresoLeccionService;

    @Autowired
    private ProgresoService progresoService;


    // ============================================
    //     MOSTRAR LECCIÓN (NO COMPLETA NADA)
    // ============================================
    @GetMapping("/{id}")
    public String verLeccion(@PathVariable Long id,
                             HttpSession session,
                             Model model) {

        // Validar sesión
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        // Obtener lección
        Leccion leccion = leccionService.obtenerPorId(id);
        Modulo modulo = leccion.getModulo();
        Long moduloId = modulo.getId();

        // ❌ ANTES aquí se completaba automáticamente. YA NO.

        // IDs de navegación
        Long anteriorId = leccionService.buscarAnteriorId(id, moduloId);
        Long siguienteId = leccionService.buscarSiguienteId(id, moduloId);

        // Enviar datos al HTML
        model.addAttribute("leccion", leccion);
        model.addAttribute("modulo", modulo);
        model.addAttribute("moduloId", moduloId);
        model.addAttribute("anteriorId", anteriorId);
        model.addAttribute("siguienteId", siguienteId);

        return "leccion";
    }


    // ============================================
    //     COMPLETAR LECCIÓN (BOTÓN SIGUIENTE)
    // ============================================
    @GetMapping("/{id}/completar")
    public String completarLeccion(@PathVariable Long id, HttpSession session) {

        // Validar sesión
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioService.buscarPorUsername(username);
        Leccion leccion = leccionService.obtenerPorId(id);
        Modulo modulo = leccion.getModulo();

        // 1️⃣ Marcar como completada (true si era nueva)
        boolean nueva = progresoLeccionService.marcarCompletada(usuario, leccion);

        // 2️⃣ Si es nueva → actualizar avance
        if (nueva) {
            progresoService.actualizarProgresoLeccion(usuario, modulo);
        }

        // 3️⃣ Buscar la siguiente lección
        Long siguienteId = leccionService.buscarSiguienteId(id, modulo.getId());

        // Sin siguiente → volver al curso
        if (siguienteId == null) {
            return "redirect:/curso/" + modulo.getId();
        }

        return "redirect:/leccion/" + siguienteId;
    }
}
