package cg.service;
import cg.model.Category;

import java.util.Optional;

public interface ICategoryService {
    Iterable<Category> findAll();

    Category findById(Long id);
}
