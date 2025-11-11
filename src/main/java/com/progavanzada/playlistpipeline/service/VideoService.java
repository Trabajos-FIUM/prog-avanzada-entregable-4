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

    public List<Video> all() {
        return repo.findAll();
    }

    public void add(String title, String url) {
        if (title == null || title.isBlank() || url == null || url.isBlank()) {
            throw new IllegalArgumentException("Título y URL son obligatorios");
        }
        if (!url.contains("youtube")) {
            throw new IllegalArgumentException("El link debe ser de YouTube");
        }
        String embedUrl = url.replace("watch?v=", "embed/");
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

