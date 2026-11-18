package br.com.fincontrol.application.command.transaction;

import br.com.fincontrol.application.exception.ResourceNotFoundException;
import br.com.fincontrol.domain.category.Category;
import br.com.fincontrol.domain.customer.Customer;
import br.com.fincontrol.domain.enums.CategoryPurpose;
import br.com.fincontrol.domain.enums.TransactionType;
import br.com.fincontrol.domain.transaction.Transaction;
import br.com.fincontrol.domain.transaction.dto.TransactionResponseDto;
import br.com.fincontrol.infra.repositories.CategoryRepository;
import br.com.fincontrol.infra.repositories.CustomerRepository;
import br.com.fincontrol.infra.repositories.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionCommandHandler {
    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;

    public TransactionCommandHandler(TransactionRepository transactionRepository,
                                     CustomerRepository customerRepository,
                                     CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public TransactionResponseDto handle(CreateTransactionCommand command) {
        Customer customer = customerRepository.findById(command.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));
        Category category = categoryRepository.findById(command.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        if (customer.getAge() < 18 && command.transactionType() != TransactionType.Expenditure) {
            throw new IllegalArgumentException("Menores de idade só podem cadastrar despesas");
        }
        CategoryPurpose requiredPurpose = command.transactionType() == TransactionType.Expenditure
                ? CategoryPurpose.Expenditure
                : CategoryPurpose.Revenue;
        if (category.getPurpose() != CategoryPurpose.Both && category.getPurpose() != requiredPurpose) {
            throw new IllegalArgumentException("A finalidade da categoria não permite esse tipo de transação");
        }

        Transaction transaction = transactionRepository.save(new Transaction(
                null,
                command.description().trim(),
                command.transactionType(),
                command.amount(),
                category.getPurpose(),
                category,
                customer));
        return toResponse(transaction);
    }

    private TransactionResponseDto toResponse(Transaction transaction) {
        return new TransactionResponseDto(
                transaction.getId(),
                transaction.getDescription(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getCustomerId().getName(),
                transaction.getCategoryId().getDescription(),
                transaction.getCategoryPurpose(),
                transaction.getCustomerId().getId(),
                transaction.getCategoryId().getId());
    }
}
