package ru.gb.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.market.models.Category;
import ru.gb.market.repositories.CategoryRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Optional<Category> findByTitle (String title) {
        return categoryRepository.findByTitle(title);
    }

    public Optional<Category> findById (Long id) {
        return categoryRepository.findById(id);
    }

    public Category create (String categoryTitle) {
        Category category = new Category();
        category.setTitle(categoryTitle);
        return categoryRepository.save(category);
    }
}
