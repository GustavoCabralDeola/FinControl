package br.com.fincontrol.api.customer;

import br.com.fincontrol.application.command.customer.*;
import br.com.fincontrol.application.query.QueryHandler;
import br.com.fincontrol.application.query.customer.ListCustomersQuery;
import br.com.fincontrol.domain.customer.dto.CreateCustomerDto;
import br.com.fincontrol.domain.customer.dto.CustomerResponseDto;
import br.com.fincontrol.domain.customer.dto.UpdateCustomerDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerCommandHandler commands;
    private final QueryHandler queries;

    public CustomerController(CustomerCommandHandler commands, QueryHandler queries) {
        this.commands = commands;
        this.queries = queries;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponseDto create(@Valid @RequestBody CreateCustomerDto request) {
        return commands.handle(new CreateCustomerCommand(request.name(), request.age()));
    }

    @GetMapping
    public List<CustomerResponseDto> list() {
        return queries.handle(new ListCustomersQuery());
    }

    @PutMapping("/{id}")
    public CustomerResponseDto update(@PathVariable UUID id, @Valid @RequestBody UpdateCustomerDto request) {
        return commands.handle(new UpdateCustomerCommand(id, request.name(), request.age()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        commands.handle(new DeleteCustomerCommand(id));
    }
}
