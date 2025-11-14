package com.progavanzada.playlistpipeline.controller;

import com.progavanzada.playlistpipeline.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    private final VideoService service;

    public HomeController(VideoService service) {
        this.service = service;
    }

    // Página principal: lista todos los videos
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("videos", service.all());
        return "index"; // -> templates/index.html
    }

    // Página para agregar un nuevo video
    @GetMapping("/add")
    public String addPage() {
        return "add"; // -> templates/add.html
    }

    // Página para ver un video individual
    @GetMapping("/view/{id}")
    public String viewVideo(@PathVariable Long id, Model model) {
        model.addAttribute("video", service.findById(id));
        return "view"; // -> templates/view.html
    }
}
