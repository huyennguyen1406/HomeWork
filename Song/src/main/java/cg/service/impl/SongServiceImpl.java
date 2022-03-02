package cg.service.impl;

import cg.model.Category;
import cg.model.Song;
import cg.repository.ISongRepository;
import cg.service.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SongServiceImpl implements ISongService {

    @Autowired
    private ISongRepository songRepository;


    @Override
    public Page<Song> findAll(Pageable pageable) {
        return songRepository.findAll(pageable);
    }

    @Override
    public Song findById(Long id) {
        if (songRepository.findById(id).isPresent()){
            return songRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public Song saveSong(Song song) {
        return songRepository.save(song);
    }

    @Override
    public void deleteSongById(Long id) {
        songRepository.deleteById(id);
    }

    @Override
    public Page<Song> getAllSongByNameContaining(String name, Pageable pageable) {
        return songRepository.findAllByNameContaining(name,pageable);
    }

    @Override
    public Page<Song> getAllSongByCategory(Category category, Pageable pageable) {
        return songRepository.findAllByCategory(category,pageable);
    }
}
