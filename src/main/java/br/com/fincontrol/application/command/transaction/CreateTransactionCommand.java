package br.com.fincontrol.application.command.transaction;

import br.com.fincontrol.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateTransactionCommand(
        String description, TransactionType transactionType, BigDecimal amount, UUID categoryId, UUID customerId) {
}
