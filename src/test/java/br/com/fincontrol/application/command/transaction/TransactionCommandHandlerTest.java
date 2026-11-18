package br.com.fincontrol.application.command.transaction;

import br.com.fincontrol.domain.category.Category;
import br.com.fincontrol.domain.customer.Customer;
import br.com.fincontrol.domain.enums.CategoryPurpose;
import br.com.fincontrol.domain.enums.TransactionType;
import br.com.fincontrol.domain.transaction.Transaction;
import br.com.fincontrol.infra.repositories.CategoryRepository;
import br.com.fincontrol.infra.repositories.CustomerRepository;
import br.com.fincontrol.infra.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionCommandHandlerTest {
    private TransactionRepository transactionRepository;
    private CustomerRepository customerRepository;
    private CategoryRepository categoryRepository;
    private TransactionCommandHandler handler;
    private UUID customerId;
    private UUID categoryId;

    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        customerRepository = mock(CustomerRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        handler = new TransactionCommandHandler(transactionRepository, customerRepository, categoryRepository);
        customerId = UUID.randomUUID();
        categoryId = UUID.randomUUID();
    }

    @Test
    void rejectsRevenueForMinor() {
        givenCustomer(17);
        givenCategory(CategoryPurpose.Both);

        assertThrows(IllegalArgumentException.class,
                () -> handler.handle(command(TransactionType.Revenue)));
        verifyNoInteractions(transactionRepository);
    }

    @Test
    void rejectsCategoryPurposeMismatch() {
        givenCustomer(30);
        givenCategory(CategoryPurpose.Expenditure);

        assertThrows(IllegalArgumentException.class,
                () -> handler.handle(command(TransactionType.Revenue)));
        verifyNoInteractions(transactionRepository);
    }

    @Test
    void acceptsExpenseForMinorWithBothPurposeCategory() {
        givenCustomer(17);
        givenCategory(CategoryPurpose.Both);
        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var response = handler.handle(command(TransactionType.Expenditure));

        assertEquals(customerId, response.customerId());
        assertEquals(categoryId, response.categoryId());
        assertEquals(CategoryPurpose.Both, response.categoryPurpose());
    }

    private CreateTransactionCommand command(TransactionType type) {
        return new CreateTransactionCommand("Teste", type, new BigDecimal("10.00"), categoryId, customerId);
    }

    private void givenCustomer(int age) {
        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(new Customer(customerId, "Pessoa", age)));
    }

    private void givenCategory(CategoryPurpose purpose) {
        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.of(new Category(categoryId, "Categoria", purpose)));
    }
}
