package br.com.fincontrol.api.summary;

import br.com.fincontrol.application.query.QueryHandler;
import br.com.fincontrol.application.query.summary.CategoryBalanceQuery;
import br.com.fincontrol.application.query.summary.PersonBalanceQuery;
import br.com.fincontrol.domain.summary.dto.CategoryBalanceReportDto;
import br.com.fincontrol.domain.summary.dto.PersonBalanceReportDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class SummaryController {
    private final QueryHandler queries;

    public SummaryController(QueryHandler queries) {
        this.queries = queries;
    }

    @GetMapping("/people")
    public PersonBalanceReportDto people() {
        return queries.handle(new PersonBalanceQuery());
    }

    @GetMapping("/categories")
    public CategoryBalanceReportDto categories() {
        return queries.handle(new CategoryBalanceQuery());
    }
}
