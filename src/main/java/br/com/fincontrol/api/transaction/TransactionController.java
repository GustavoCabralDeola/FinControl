package br.com.fincontrol.api.transaction;

import br.com.fincontrol.application.command.transaction.CreateTransactionCommand;
import br.com.fincontrol.application.command.transaction.TransactionCommandHandler;
import br.com.fincontrol.application.query.QueryHandler;
import br.com.fincontrol.application.query.transaction.ListTransactionsQuery;
import br.com.fincontrol.domain.transaction.dto.CreateTransactionDto;
import br.com.fincontrol.domain.transaction.dto.TransactionResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionCommandHandler commands;
    private final QueryHandler queries;

    public TransactionController(TransactionCommandHandler commands, QueryHandler queries) {
        this.commands = commands;
        this.queries = queries;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponseDto create(@Valid @RequestBody CreateTransactionDto request) {
        return commands.handle(new CreateTransactionCommand(request.description(), request.transactionType(),
                request.amount(), request.categoryId(), request.customerId()));
    }

    @GetMapping
    public List<TransactionResponseDto> list() {
        return queries.handle(new ListTransactionsQuery());
    }
}
