package com.henjid.henjid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // ===============================
    // PÁGINA PRINCIPAL
    // ===============================
    @GetMapping("/")
    public String index() {
        return "index"; // index.html
    }

    // ===============================
    // REDIRECCIÓN SI ALGUIEN PONE /home
    // ===============================
    @GetMapping("/home")
    public String homeRedirect() {
        return "redirect:/";
    }
}
