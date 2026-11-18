package br.com.fincontrol.application.command.customer;

import java.util.UUID;

public record UpdateCustomerCommand(UUID id, String name, int age) {
}
