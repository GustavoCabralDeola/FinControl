package br.com.fincontrol.domain.summary.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PersonBalanceDto(UUID customerId, String name, BigDecimal revenues,
                               BigDecimal expenditures, BigDecimal balance) {
}
