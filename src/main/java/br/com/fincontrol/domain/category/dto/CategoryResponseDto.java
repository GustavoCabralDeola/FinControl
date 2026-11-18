package br.com.fincontrol.domain.category.dto;

import br.com.fincontrol.domain.enums.CategoryPurpose;

import java.util.UUID;

public record CategoryResponseDto(UUID id, String description, CategoryPurpose purpose) {
}
