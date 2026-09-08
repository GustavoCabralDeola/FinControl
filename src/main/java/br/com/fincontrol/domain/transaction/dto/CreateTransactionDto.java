package br.com.fincontrol.domain.transaction.dto;

import br.com.fincontrol.domain.enums.CategoryPurpose;
import br.com.fincontrol.domain.enums.TransactionType;

public record CreateTransactionDto(String description, TransactionType transactionType, double amount, int categoryId, int customerId){

}
