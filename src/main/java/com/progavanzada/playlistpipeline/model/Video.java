package com.progavanzada.playlistpipeline.model;

import jakarta.persistence.*;

@Entity
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String url;
    private int likes = 0;
    private boolean favorite = false;

    public Video() {}

    public Video(String title, String url) {
        this.title = title;
        this.url = url;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }

    public boolean isFavorite() { return favorite; }
    public void setFavorite(boolean favorite) { this.favorite = favorite; }
}

