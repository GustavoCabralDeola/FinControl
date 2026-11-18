package br.com.fincontrol.domain.transaction.dto;

import br.com.fincontrol.domain.enums.CategoryPurpose;
import br.com.fincontrol.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionResponseDto(
        UUID id,
        String description,
        TransactionType transactionType,
        BigDecimal amount,
        String customerName,
        String categoryDescription,
        CategoryPurpose categoryPurpose,
        UUID customerId,
        UUID categoryId) {
}
