package br.com.fincontrol.application.command.customer;

import br.com.fincontrol.domain.customer.Customer;
import br.com.fincontrol.domain.customer.dto.CustomerResponseDto;
import br.com.fincontrol.infra.repositories.CustomerRepository;
import br.com.fincontrol.infra.repositories.TransactionRepository;
import br.com.fincontrol.application.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerCommandHandler {
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public CustomerCommandHandler(CustomerRepository customerRepository, TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public CustomerResponseDto handle(CreateCustomerCommand command) {
        Customer customer = customerRepository.save(new Customer(null, command.name().trim(), command.age()));
        return toResponse(customer);
    }

    @Transactional
    public CustomerResponseDto handle(UpdateCustomerCommand command) {
        Customer customer = customerRepository.findById(command.id())
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));
        customer.setName(command.name().trim());
        customer.setAge(command.age());
        return toResponse(customerRepository.save(customer));
    }

    @Transactional
    public void handle(DeleteCustomerCommand command) {
        if (!customerRepository.existsById(command.id())) {
            throw new ResourceNotFoundException("Pessoa não encontrada");
        }
        transactionRepository.deleteByCustomerId_Id(command.id());
        customerRepository.deleteById(command.id());
    }

    private CustomerResponseDto toResponse(Customer customer) {
        return new CustomerResponseDto(customer.getId(), customer.getName(), customer.getAge());
    }
}
