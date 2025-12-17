package com.henjid.henjid.controller;

import com.henjid.henjid.model.Leccion;
import com.henjid.henjid.model.Modulo;
import com.henjid.henjid.model.Progreso;
import com.henjid.henjid.model.Usuario;
import com.henjid.henjid.service.ActividadService;
import com.henjid.henjid.service.LeccionService;
import com.henjid.henjid.service.ModuloService;
import com.henjid.henjid.service.ProgresoLeccionService;
import com.henjid.henjid.service.ProgresoService;
import com.henjid.henjid.service.UsuarioService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class ModuloController {

    @Autowired
    private ModuloService moduloService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProgresoService progresoService;

    @Autowired
    private LeccionService leccionService;

    @Autowired
    private ActividadService actividadService;

    @Autowired
    private ProgresoLeccionService progresoLeccionService;



    // =====================================================
    //   MOSTRAR TODOS LOS MÓDULOS (con bloqueo por nivel)
    // =====================================================
    @GetMapping("/modulos")
    public String mostrarModulos(Model model, HttpSession session) {

        String username = (String) session.getAttribute("username");
        Usuario usuario = null;

        if (username != null) {
            usuario = usuarioService.buscarPorUsername(username);
        }

        List<Modulo> modulos = moduloService.listarTodos();
        Map<Long, Boolean> bloqueado = new HashMap<>();

        boolean a1Comp = false;
        boolean a2Comp = false;
        boolean b1Comp = false;

        if (usuario != null) {

            for (Modulo m : modulos) {

                switch (m.getNivel()) {
                    case "A1":
                        bloqueado.put(m.getId(), false);
                        a1Comp = progresoService.estaCompletado(usuario, m);
                        break;

                    case "A2":
                        bloqueado.put(m.getId(), !a1Comp);
                        a2Comp = progresoService.estaCompletado(usuario, m);
                        break;

                    case "B1":
                        bloqueado.put(m.getId(), !a2Comp);
                        b1Comp = progresoService.estaCompletado(usuario, m);
                        break;

                    case "B2":
                        bloqueado.put(m.getId(), !b1Comp);
                        break;

                    default:
                        bloqueado.put(m.getId(), false);
                }
            }

        } else {
            for (Modulo m : modulos) {
                bloqueado.put(m.getId(), false);
            }
        }

        model.addAttribute("modulos", modulos);
        model.addAttribute("bloqueado", bloqueado);

        return "modulos";
    }



    // =====================================================
    //   FILTRAR POR NIVEL
    // =====================================================
    @GetMapping("/modulos/nivel/{nivel}")
    public String filtrarNivel(@PathVariable String nivel, Model model) {

        List<Modulo> modulos = moduloService.listarPorNivel(nivel);
        model.addAttribute("modulos", modulos);
        model.addAttribute("nivelSeleccionado", nivel);

        return "modulos";
    }



    // =====================================================
    //   MOSTRAR CURSO (curso.html)
    // =====================================================
    @GetMapping("/curso/{id}")
    public String mostrarCurso(@PathVariable Long id, Model model, HttpSession session) {

        // Obtener módulo
        Modulo modulo = moduloService.obtenerPorId(id);
        if (modulo == null) {
            return "redirect:/modulos";
        }

        // Obtener usuario
        String username = (String) session.getAttribute("username");
        Usuario usuario = null;
        if (username != null) {
            usuario = usuarioService.buscarPorUsername(username);
        }

        if (usuario != null) {
    model.addAttribute("ultimaActividadUsuario", usuario.getUltimaActividad());
}


        // ============================================
        // 🔥 REGISTRAR ACTIVIDAD DEL USUARIO
        // ============================================
        Progreso progreso = null;

        if (usuario != null) {
            progresoService.registrarActividad(usuario.getId(), modulo.getId());
            progreso = progresoService.obtenerOcrear(usuario, modulo);
        }

        // ============================================
        // 🔥 FORMATEAR TIEMPO INVERTIDO
        // ============================================
        String tiempoFormateado = "--";

        if (progreso != null && progreso.getTiempoTotalSegundos() != null) {

            long total = progreso.getTiempoTotalSegundos();

            if (total > 0) {
                long horas = total / 3600;
                long minutos = (total % 3600) / 60;

                if (horas > 0) {
                    tiempoFormateado = horas + "h " + minutos + "min";
                } else {
                    tiempoFormateado = minutos + "min";
                }
            }
        }

        model.addAttribute("historial", progreso);
        model.addAttribute("tiempoFormateado", tiempoFormateado);

        // ============================================
        // LECCIONES DEL MÓDULO
        // ============================================
        List<Leccion> lecciones = leccionService.getLeccionesByModulo(id);

        Map<Long, Boolean> estadoLeccion = new HashMap<>();

        if (usuario != null) {
            for (Leccion l : lecciones) {
                boolean completada = progresoLeccionService.estaCompletada(usuario, l);
                estadoLeccion.put(l.getId(), completada);
            }
        } else {
            for (Leccion l : lecciones) {
                estadoLeccion.put(l.getId(), false);
            }
        }

        // 🔥 CONTAR LECCIONES COMPLETADAS (para el quiz)
        long totalCompletadas = estadoLeccion.values()
                .stream()
                .filter(v -> v)
                .count();

        model.addAttribute("completadas", totalCompletadas);

        // Enviar datos al HTML
        model.addAttribute("modulo", modulo);
        model.addAttribute("lecciones", lecciones);
        model.addAttribute("estadoLeccion", estadoLeccion);
        model.addAttribute("actividades", actividadService.getActividadesByModulo(id));

        return "curso";
    }


@GetMapping("/crucigrama")
public String crucigrama(@RequestParam Long moduloId, Model model) {
    model.addAttribute("moduloId", moduloId);
    return "crucigrama";
}


@GetMapping("/dragdrop")
public String dragdrop(@RequestParam Long moduloId, Model model) {
    model.addAttribute("moduloId", moduloId);
    return "dragdrop";
}


}
