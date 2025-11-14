package com.progavanzada.playlistpipeline.service;


import com.progavanzada.playlistpipeline.model.Video;
import com.progavanzada.playlistpipeline.repository.VideoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VideoService {
    private final VideoRepository repo;

    public VideoService(VideoRepository repo) {
        this.repo = repo;
    }

    public Video findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Video no encontrado"));
    }

    public List<Video> all() {
        return repo.findAll();
    }

    public void add(String title, String url) {
        if (title == null || title.isBlank() || url == null || url.isBlank()) {
            throw new IllegalArgumentException("Título y URL son obligatorios");
        }

        // Aceptar tanto youtube.com como youtu.be
        if (!url.contains("youtube.com") && !url.contains("youtu.be")) {
            throw new IllegalArgumentException("El link debe ser de YouTube");
        }

        // Limpiar y normalizar URL
        String videoId = null;

        if (url.contains("watch?v=")) {
            videoId = url.split("watch\\?v=")[1].split("&")[0];
        } else if (url.contains("youtu.be/")) {
            videoId = url.split("youtu.be/")[1].split("\\?")[0];
        }

        if (videoId == null || videoId.isEmpty()) {
            throw new IllegalArgumentException("No se pudo obtener el ID del video de YouTube");
        }

        String embedUrl = "https://www.youtube.com/embed/" + videoId;
        repo.save(new Video(title, embedUrl));
    }




    public void delete(Long id) {
        repo.deleteById(id);
    }

    public void like(Long id) {
        repo.findById(id).ifPresent(v -> {
            v.setLikes(v.getLikes() + 1);
            repo.save(v);
        });
    }

    public void toggleFavorite(Long id) {
        repo.findById(id).ifPresent(v -> {
            v.setFavorite(!v.isFavorite());
            repo.save(v);
        });
    }
}

