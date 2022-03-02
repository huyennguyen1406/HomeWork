package cg.service;

import cg.model.Human;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;

import java.util.Optional;
public interface IHumanService {
    Page<Human> findAll(Pageable pageable);
    Human save(Human human);
    void delete(Long id);
    Optional<Human> findOne(Long id);
    Page<Human> findByName(String name, Pageable pageable);
}
