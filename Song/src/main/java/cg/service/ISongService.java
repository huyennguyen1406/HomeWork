package cg.service;

import cg.model.Category;
import cg.model.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISongService {
    Page<Song> findAll(Pageable pageable);

    Song findById(Long id);

    Song saveSong(Song song);

    void deleteSongById(Long id);

    Page<Song> getAllSongByNameContaining(String name, Pageable pageable);

    Page<Song> getAllSongByCategory(Category category, Pageable pageable);
}
