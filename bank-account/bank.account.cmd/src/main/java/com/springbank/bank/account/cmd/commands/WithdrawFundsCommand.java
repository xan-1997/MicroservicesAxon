package com.springbank.bank.account.cmd.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class WithdrawFundsCommand {
    @TargetAggregateIdentifier
    private String id;
    private double amount;
}
