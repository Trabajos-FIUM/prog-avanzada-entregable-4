package com.progavanzada.playlistpipeline.service;

import com.progavanzada.playlistpipeline.repository.VideoRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class VideoServiceTest {

    @Test
    void addShouldFailForNonYoutubeLink() {
        // Mock del repositorio
        VideoRepository repo = mock(VideoRepository.class);

        // Pasamos el mock al servicio
        VideoService service = new VideoService(repo);

        // Ejecutamos la lógica que debe fallar
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.add("Video inválido", "https://google.com")
        );

        // Validamos el mensaje
        assertEquals("El link debe ser de YouTube", ex.getMessage());
    }
}
