package br.com.fincontrol.domain.transaction.dto;

import br.com.fincontrol.domain.enums.CategoryPurpose;
import br.com.fincontrol.domain.enums.TransactionType;

public record TransactionResponseDto(String description, TransactionType transactionType, double amount, String customerName, String categoryDescription, CategoryPurpose categoryPurpose, int customerId, int categoryId)
{}
