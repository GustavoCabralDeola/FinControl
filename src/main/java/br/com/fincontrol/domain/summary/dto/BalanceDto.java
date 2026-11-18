package br.com.fincontrol.domain.summary.dto;

import java.math.BigDecimal;

public record BalanceDto(BigDecimal revenues, BigDecimal expenditures, BigDecimal balance) {
}
