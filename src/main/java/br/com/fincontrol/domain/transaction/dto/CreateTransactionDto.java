package br.com.fincontrol.domain.transaction.dto;

import br.com.fincontrol.domain.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateTransactionDto(
        @NotBlank @Size(max = 400) String description,
        @NotNull TransactionType transactionType,
        @NotNull @DecimalMin(value = "0.00", inclusive = false) @Digits(integer = 17, fraction = 2) BigDecimal amount,
        @NotNull UUID categoryId,
        @NotNull UUID customerId) {
}
