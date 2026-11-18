package br.com.fincontrol.domain.summary.dto;

import br.com.fincontrol.domain.enums.CategoryPurpose;

import java.math.BigDecimal;
import java.util.UUID;

public record CategoryBalanceDto(UUID categoryId, String description, CategoryPurpose purpose,
                                 BigDecimal revenues, BigDecimal expenditures, BigDecimal balance) {
}
