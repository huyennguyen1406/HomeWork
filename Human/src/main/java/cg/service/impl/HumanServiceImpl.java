package cg.service.impl;

import cg.model.Human;
import cg.repository.IHumanRepository;
import cg.service.IHumanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HumanServiceImpl implements IHumanService {
    @Autowired
    IHumanRepository humanRepository;

    @Override
    public Page<Human> findAll(Pageable pageable) {
        return humanRepository.findAll(pageable);
    }

    @Override
    public Human save(Human human) {
        return humanRepository.save(human);
    }

    @Override
    public void delete(Long id) {
        humanRepository.deleteById(id);
    }

    @Override
    public Optional<Human> findOne(Long id) {
        return humanRepository.findById(id);
    }

    @Override
    public Page<Human> findByName(String name, Pageable pageable) {
        return humanRepository.findByNameContaining(name,pageable);
    }
}
