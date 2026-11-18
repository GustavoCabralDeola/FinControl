package br.com.fincontrol.domain.summary.dto;

import java.util.List;

public record CategoryBalanceReportDto(List<CategoryBalanceDto> categories, BalanceDto total) {
}
