package br.com.fincontrol.application.command.category;

import br.com.fincontrol.domain.enums.CategoryPurpose;

public record CreateCategoryCommand(String description, CategoryPurpose purpose) {
}
