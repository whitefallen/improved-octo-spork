package com.example.app.song.service;

import com.example.app.song.domain.Song;
import com.example.app.song.dto.SongRequest;
import com.example.app.song.dto.SongResponse;
import com.example.app.song.repository.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SongServiceImplTest {

    @Mock
    private SongRepository songRepository;

    @InjectMocks
    private SongServiceImpl songService;

    private Song song;
    private SongRequest songRequest;

    @BeforeEach
    void setUp() {
        song = new Song();
        song.setTitle("Test Song");
        song.setArtist("Test Artist");
        song.setAlbum("Test Album");
        song.setGenre("Rock");
        song.setDurationSeconds(240);

        songRequest = new SongRequest();
        songRequest.setTitle("Test Song");
        songRequest.setArtist("Test Artist");
        songRequest.setAlbum("Test Album");
        songRequest.setGenre("Rock");
        songRequest.setDurationSeconds(240);
    }

    @Test
    void findAll_returnsAllSongs() {
        when(songRepository.findAll()).thenReturn(Arrays.asList(song));
        List<SongResponse> result = songService.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Song");
    }

    @Test
    void findById_found() {
        when(songRepository.findById(1L)).thenReturn(Optional.of(song));
        Optional<SongResponse> result = songService.findById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getArtist()).isEqualTo("Test Artist");
    }

    @Test
    void findById_notFound() {
        when(songRepository.findById(99L)).thenReturn(Optional.empty());
        assertThat(songService.findById(99L)).isEmpty();
    }

    @Test
    void save_persistsSong() {
        when(songRepository.save(any(Song.class))).thenReturn(song);
        SongResponse saved = songService.save(songRequest);
        assertThat(saved).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("Test Song");
        verify(songRepository).save(any(Song.class));
    }

    @Test
    void update_existingSong() {
        Song existing = new Song();
        existing.setTitle("Old Title");
        when(songRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(songRepository.save(any(Song.class))).thenReturn(existing);

        SongResponse updated = songService.update(1L, songRequest);
        assertThat(updated.getTitle()).isEqualTo("Test Song");
    }

    @Test
    void update_notFound_throwsException() {
        when(songRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> songService.update(99L, songRequest))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deleteById_callsRepository() {
        doNothing().when(songRepository).deleteById(1L);
        songService.deleteById(1L);
        verify(songRepository).deleteById(1L);
    }
}
