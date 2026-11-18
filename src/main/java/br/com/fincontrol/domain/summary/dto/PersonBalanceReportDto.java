package br.com.fincontrol.domain.summary.dto;

import java.util.List;

public record PersonBalanceReportDto(List<PersonBalanceDto> people, BalanceDto total) {
}
