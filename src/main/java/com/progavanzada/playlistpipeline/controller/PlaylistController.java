package com.progavanzada.playlistpipeline.controller;

import com.progavanzada.playlistpipeline.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PlaylistController {
    private final VideoService service;

    public PlaylistController(VideoService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("videos", service.all());
        return "index";
    }

    @PostMapping("/add")
    public String add(@RequestParam String title, @RequestParam String url) {
        service.add(title, url);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/";
    }

    @PostMapping("/like/{id}")
    public String like(@PathVariable Long id) {
        service.like(id);
        return "redirect:/";
    }

    @PostMapping("/fav/{id}")
    public String fav(@PathVariable Long id) {
        service.toggleFavorite(id);
        return "redirect:/";
    }
}

