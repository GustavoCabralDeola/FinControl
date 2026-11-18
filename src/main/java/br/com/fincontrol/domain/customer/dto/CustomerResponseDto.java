package br.com.fincontrol.domain.customer.dto;

import java.util.UUID;

public record CustomerResponseDto(UUID id, String name, int age) {
}
