package br.com.fincontrol.domain.category.dto;

import br.com.fincontrol.domain.enums.CategoryPurpose;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCategoryDto(
        @NotBlank @Size(max = 400) String description,
        @NotNull CategoryPurpose purpose) {
}
