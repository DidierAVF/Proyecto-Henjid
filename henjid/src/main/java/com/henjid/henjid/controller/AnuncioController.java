package com.henjid.henjid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Random;

@Controller
public class AnuncioController {

    private final Random random = new Random();

    @GetMapping("/anuncio")
    public String mostrarAnuncio(@RequestParam(required = false) Long moduloId,
                                 Model model) {

        // Lista de videos disponibles en /static/videos
        List<String> videos = List.of("anuncio1.mp4", "anuncio2.mp4", "anuncio3.mp4");
        String videoFile = videos.get(random.nextInt(videos.size()));

        model.addAttribute("videoFile", videoFile);
        model.addAttribute("moduloId", moduloId); // para volver al curso si quieres

        return "anuncio"; // templates/anuncio.html
    }
}
