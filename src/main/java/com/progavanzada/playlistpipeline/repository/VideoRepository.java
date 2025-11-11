package com.progavanzada.playlistpipeline.repository;

import com.progavanzada.playlistpipeline.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {}

