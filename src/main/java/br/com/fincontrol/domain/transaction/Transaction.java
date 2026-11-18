package br.com.fincontrol.domain.transaction;

import br.com.fincontrol.domain.category.Category;
import br.com.fincontrol.domain.customer.Customer;
import br.com.fincontrol.domain.enums.CategoryPurpose;
import br.com.fincontrol.domain.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "transactions")
@Entity
@NoArgsConstructor
@Getter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 400)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private CategoryPurpose categoryPurpose;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category categoryId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customerId;

    public Transaction(UUID id, String description, TransactionType transactionType, BigDecimal amount,
                       CategoryPurpose categoryPurpose, Category categoryId, Customer customerId) {
        this.id = id;
        this.description = description;
        this.transactionType = transactionType;
        this.amount = amount;
        this.categoryPurpose = categoryPurpose;
        this.categoryId = categoryId;
        this.customerId = customerId;
    }

    public UUID getId() { return id; }
    public String getDescription() { return description; }
    public TransactionType getTransactionType() { return transactionType; }
    public BigDecimal getAmount() { return amount; }
    public CategoryPurpose getCategoryPurpose() { return categoryPurpose; }
    public Category getCategoryId() { return categoryId; }
    public Customer getCustomerId() { return customerId; }
}
