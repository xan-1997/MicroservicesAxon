package com.springbank.bank.account.cmd.commands;

import com.springbank.bank.account.core.models.AccountType;
import com.springbank.bank.account.core.models.BankAccount;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.springframework.context.annotation.Bean;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class OpenAccountCommand {
    @TargetAggregateIdentifier
    private String id;
    @NotNull(message = "no account holder ID was supplied")
    private String accountHolderId;
    @NotNull(message = "no account type ID was supplied")
    private AccountType accountType;
    @Min(value = 50, message = "opening balance must be at least 50.")
    private double openingBalance;
    private BankAccount bankAccount;
}
