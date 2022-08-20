package ru.gb.market.services;

import org.springframework.stereotype.Service;
import ru.gb.market.models.Category;
import ru.gb.market.repositories.CategoryRepository;

import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Optional<Category> findByTitle(String title) {
        return categoryRepository.findByTitle(title);
    }
}
