package br.com.fincontrol.domain.transaction;

import br.com.fincontrol.domain.category.Category;
import br.com.fincontrol.domain.customer.Customer;
import br.com.fincontrol.domain.enums.CategoryPurpose;
import br.com.fincontrol.domain.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "transactions")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue
    private UUID id;

    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private double amount;

    @Enumerated(EnumType.STRING)
    private CategoryPurpose categoryPurpose;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customerId;
}
