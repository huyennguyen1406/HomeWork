package cg.repository;

import cg.model.Human;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHumanRepository extends JpaRepository<Human,Long> {
    Page<Human> findByNameContaining(String name, Pageable pageable);
}
