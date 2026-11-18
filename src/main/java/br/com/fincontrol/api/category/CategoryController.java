package br.com.fincontrol.api.category;

import br.com.fincontrol.application.command.category.CategoryCommandHandler;
import br.com.fincontrol.application.command.category.CreateCategoryCommand;
import br.com.fincontrol.application.query.QueryHandler;
import br.com.fincontrol.application.query.category.ListCategoriesQuery;
import br.com.fincontrol.domain.category.dto.CategoryResponseDto;
import br.com.fincontrol.domain.category.dto.CreateCategoryDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryCommandHandler commands;
    private final QueryHandler queries;

    public CategoryController(CategoryCommandHandler commands, QueryHandler queries) {
        this.commands = commands;
        this.queries = queries;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto create(@Valid @RequestBody CreateCategoryDto request) {
        return commands.handle(new CreateCategoryCommand(request.description(), request.purpose()));
    }

    @GetMapping
    public List<CategoryResponseDto> list() {
        return queries.handle(new ListCategoriesQuery());
    }
}
