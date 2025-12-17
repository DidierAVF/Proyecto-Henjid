package com.henjid.henjid.controller;

import com.henjid.henjid.model.Modulo;
import com.henjid.henjid.model.Usuario;
import com.henjid.henjid.service.ModuloService;
import com.henjid.henjid.service.ProgresoService;
import com.henjid.henjid.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ModuloService moduloService;

    @Autowired
    private ProgresoService progresoService;

    // Mostrar el quiz
    @GetMapping("/{moduloId}")
    public String mostrarQuiz(@PathVariable Long moduloId,
                              HttpSession session,
                              Model model) {

        String username = (String) session.getAttribute("username");
        if (username == null) return "redirect:/login";

        Usuario usuario = usuarioService.buscarPorUsername(username);
        Modulo modulo = moduloService.obtenerPorId(moduloId);

        model.addAttribute("modulo", modulo);
        model.addAttribute("usuario", usuario);

        return "quiz";
    }

  @PostMapping("/{moduloId}/resultado")
@ResponseBody
public String guardarResultado(@PathVariable Long moduloId,
                               @RequestParam int puntuacion,
                               HttpSession session) {

    String username = (String) session.getAttribute("username");
    if (username == null) return "NO_LOGIN";

    Usuario usuario = usuarioService.buscarPorUsername(username);
    Modulo modulo = moduloService.obtenerPorId(moduloId);

    // 1️⃣ Guardar la nota SIEMPRE
    progresoService.guardarNotaQuiz(usuario, modulo, puntuacion);

    // 2️⃣ Si pasó el examen, marcar completado
    if (puntuacion >= 6) {
        progresoService.marcarCompletado(usuario, modulo);
        return "APROBADO";
    }

    return "REPROBADO";
}

}
