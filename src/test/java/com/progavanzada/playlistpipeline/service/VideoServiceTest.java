package com.progavanzada.playlistpipeline.service;

import com.progavanzada.playlistpipeline.model.Video;
import com.progavanzada.playlistpipeline.repository.VideoRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

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

    @Test
    void addShouldHandleShortYoutubeUrlWithParameters() {
        VideoRepository repo = mock(VideoRepository.class);
        VideoService service = new VideoService(repo);

        String url = "https://youtu.be/VpklgTNNmTA?si=sRwWWV_HFDgzW-yH";

        service.add("Video Test", url);

        verify(repo).save(argThat(video ->
                video.getTitle().equals("Video Test") &&
                        video.getUrl().equals("https://www.youtube.com/embed/VpklgTNNmTA")
        ));
    }

    @Test
    void addShouldHandleYoutubeWatchUrlWithExtraParameters() {
        VideoRepository repo = mock(VideoRepository.class);
        VideoService service = new VideoService(repo);

        String url = "https://www.youtube.com/watch?v=VpklgTNNmTA&feature=youtu.be";

        service.add("Video Normal", url);

        verify(repo).save(argThat(video ->
                video.getTitle().equals("Video Normal") &&
                        video.getUrl().equals("https://www.youtube.com/embed/VpklgTNNmTA")
        ));
    }

    @Test
    void addShouldFailWhenTitleIsBlank() {
        VideoRepository repo = mock(VideoRepository.class);
        VideoService service = new VideoService(repo);

        assertThrows(IllegalArgumentException.class,
                () -> service.add("", "https://youtu.be/123")
        );
    }

    @Test
    void addShouldFailWhenUrlIsBlank() {
        VideoRepository repo = mock(VideoRepository.class);
        VideoService service = new VideoService(repo);

        assertThrows(IllegalArgumentException.class,
                () -> service.add("Video", "")
        );
    }

    @Test
    void addShouldFailWhenVideoIdCannotBeExtracted() {
        VideoRepository repo = mock(VideoRepository.class);
        VideoService service = new VideoService(repo);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.add("Mi video", "https://youtube.com/no-id-here")
        );

        assertEquals("No se pudo obtener el ID del video de YouTube", ex.getMessage());
    }

    @Test
    void likeShouldIncreaseLikes() {
        VideoRepository repo = mock(VideoRepository.class);
        VideoService service = new VideoService(repo);

        Video video = new Video("Titulo", "url");
        video.setLikes(2);

        when(repo.findById(1L)).thenReturn(java.util.Optional.of(video));

        service.like(1L);

        assertEquals(3, video.getLikes());
        verify(repo).save(video);
    }


    @Test
    void toggleFavoriteShouldFlipFavoriteFlag() {
        VideoRepository repo = mock(VideoRepository.class);
        VideoService service = new VideoService(repo);

        Video video = new Video("Titulo", "url");
        video.setFavorite(false);

        when(repo.findById(1L)).thenReturn(java.util.Optional.of(video));

        service.toggleFavorite(1L);

        assertTrue(video.isFavorite());
        verify(repo).save(video);
    }

    @Test
    void toggleFavoriteShouldFlipBackOnSecondCall() {
        VideoRepository repo = mock(VideoRepository.class);
        VideoService service = new VideoService(repo);

        Video video = new Video("Video", "url");
        video.setFavorite(true);

        when(repo.findById(1L)).thenReturn(java.util.Optional.of(video));

        service.toggleFavorite(1L);

        assertFalse(video.isFavorite());
        verify(repo).save(video);
    }
}
