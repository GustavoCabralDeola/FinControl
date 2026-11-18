package br.com.fincontrol.application.command.category;

import br.com.fincontrol.domain.category.Category;
import br.com.fincontrol.domain.category.dto.CategoryResponseDto;
import br.com.fincontrol.infra.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryCommandHandler {
    private final CategoryRepository categoryRepository;

    public CategoryCommandHandler(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public CategoryResponseDto handle(CreateCategoryCommand command) {
        Category category = categoryRepository.save(
                new Category(null, command.description().trim(), command.purpose()));
        return new CategoryResponseDto(category.getId(), category.getDescription(), category.getPurpose());
    }
}
