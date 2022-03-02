package cg.repository;

import cg.model.Category;
import cg.model.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISongRepository extends PagingAndSortingRepository<Song, Long> {

    Page<Song> findAllByCategory(Category category, Pageable pageable);

    Page<Song> findAllByNameContaining(String name, Pageable pageable);
}
