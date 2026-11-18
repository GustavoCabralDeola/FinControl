package br.com.fincontrol.infra.repositories;

import br.com.fincontrol.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    void deleteByCustomerId_Id(UUID customerId);
}
