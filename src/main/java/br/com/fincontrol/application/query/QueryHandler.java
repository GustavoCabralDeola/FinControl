package br.com.fincontrol.application.query;

import br.com.fincontrol.application.query.category.ListCategoriesQuery;
import br.com.fincontrol.application.query.customer.ListCustomersQuery;
import br.com.fincontrol.application.query.summary.CategoryBalanceQuery;
import br.com.fincontrol.application.query.summary.PersonBalanceQuery;
import br.com.fincontrol.application.query.transaction.ListTransactionsQuery;
import br.com.fincontrol.domain.category.dto.CategoryResponseDto;
import br.com.fincontrol.domain.customer.dto.CustomerResponseDto;
import br.com.fincontrol.domain.enums.TransactionType;
import br.com.fincontrol.domain.summary.dto.*;
import br.com.fincontrol.domain.transaction.Transaction;
import br.com.fincontrol.domain.transaction.dto.TransactionResponseDto;
import br.com.fincontrol.infra.repositories.CategoryRepository;
import br.com.fincontrol.infra.repositories.CustomerRepository;
import br.com.fincontrol.infra.repositories.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class QueryHandler {
    private static final BigDecimal ZERO = BigDecimal.ZERO;

    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    public QueryHandler(CustomerRepository customerRepository,
                        CategoryRepository categoryRepository,
                        TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<CustomerResponseDto> handle(ListCustomersQuery query) {
        return customerRepository.findAll().stream()
                .map(customer -> new CustomerResponseDto(customer.getId(), customer.getName(), customer.getAge()))
                .toList();
    }

    public List<CategoryResponseDto> handle(ListCategoriesQuery query) {
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryResponseDto(
                        category.getId(), category.getDescription(), category.getPurpose()))
                .toList();
    }

    public List<TransactionResponseDto> handle(ListTransactionsQuery query) {
        return transactionRepository.findAll().stream().map(this::toTransactionResponse).toList();
    }

    public PersonBalanceReportDto handle(PersonBalanceQuery query) {
        List<Transaction> transactions = transactionRepository.findAll();
        var customers = customerRepository.findAll();
        Map<UUID, BalanceAccumulator> balances = customers.stream()
                .collect(Collectors.toMap(customer -> customer.getId(), customer -> new BalanceAccumulator()));

        for (Transaction transaction : transactions) {
            BalanceAccumulator balance = balances.get(transaction.getCustomerId().getId());
            add(balance, transaction);
        }

        List<PersonBalanceDto> people = customers.stream()
                .map(customer -> {
                    BalanceDto balance = balances.get(customer.getId()).toDto();
                    return new PersonBalanceDto(customer.getId(), customer.getName(),
                            balance.revenues(), balance.expenditures(), balance.balance());
                })
                .toList();
        return new PersonBalanceReportDto(people, total(transactions));
    }

    public CategoryBalanceReportDto handle(CategoryBalanceQuery query) {
        List<Transaction> transactions = transactionRepository.findAll();
        Map<UUID, BalanceAccumulator> balances = categoryRepository.findAll().stream()
                .collect(Collectors.toMap(category -> category.getId(), category -> new BalanceAccumulator()));

        for (Transaction transaction : transactions) {
            BalanceAccumulator balance = balances.get(transaction.getCategoryId().getId());
            add(balance, transaction);
        }

        List<CategoryBalanceDto> categories = categoryRepository.findAll().stream()
                .map(category -> {
                    BalanceDto balance = balances.get(category.getId()).toDto();
                    return new CategoryBalanceDto(category.getId(), category.getDescription(), category.getPurpose(),
                            balance.revenues(), balance.expenditures(), balance.balance());
                })
                .toList();
        return new CategoryBalanceReportDto(categories, total(transactions));
    }

    private TransactionResponseDto toTransactionResponse(Transaction transaction) {
        return new TransactionResponseDto(transaction.getId(), transaction.getDescription(),
                transaction.getTransactionType(), transaction.getAmount(),
                transaction.getCustomerId().getName(), transaction.getCategoryId().getDescription(),
                transaction.getCategoryPurpose(), transaction.getCustomerId().getId(),
                transaction.getCategoryId().getId());
    }

    private void add(BalanceAccumulator balance, Transaction transaction) {
        if (transaction.getTransactionType() == TransactionType.Revenue) {
            balance.revenues = balance.revenues.add(transaction.getAmount());
        } else {
            balance.expenditures = balance.expenditures.add(transaction.getAmount());
        }
    }

    private BalanceDto total(List<Transaction> transactions) {
        BalanceAccumulator total = new BalanceAccumulator();
        transactions.forEach(transaction -> add(total, transaction));
        return total.toDto();
    }

    private static class BalanceAccumulator {
        private BigDecimal revenues = ZERO;
        private BigDecimal expenditures = ZERO;

        private BalanceDto toDto() {
            return new BalanceDto(revenues, expenditures, revenues.subtract(expenditures));
        }
    }
}
