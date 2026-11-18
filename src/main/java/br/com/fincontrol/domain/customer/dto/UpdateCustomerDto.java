package br.com.fincontrol.domain.customer.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateCustomerDto(
        @NotBlank @Size(max = 200) String name,
        @NotNull @Min(0) Integer age) {
}
