package com.springbank.bank.account.cmd.aggregates;

import com.springbank.bank.account.cmd.commands.CloseAccountCommand;
import com.springbank.bank.account.cmd.commands.DepositFundsCommand;
import com.springbank.bank.account.cmd.commands.OpenAccountCommand;
import com.springbank.bank.account.cmd.commands.WithdrawFundsCommand;
import com.springbank.bank.account.core.events.AccountClosedEvent;
import com.springbank.bank.account.core.events.AccountOpenedEvent;
import com.springbank.bank.account.core.events.FundsDepositedEvent;
import com.springbank.bank.account.core.events.FundsWithdrawnEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String id;
    private String accountHolderId;
    private double balance;

    public AccountAggregate(){

    }

    @CommandHandler
    public AccountAggregate(OpenAccountCommand command){
        AccountOpenedEvent event = AccountOpenedEvent
                .builder()
                .id(command.getId())
                .accountType(command.getAccountType())
                .accountHolderId(command.getAccountHolderId())
                .creationDate(new Date())
                .openingBalance(command.getOpeningBalance())
                .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AccountOpenedEvent event){
        this.id = event.getId();
        this.accountHolderId= event.getAccountHolderId();
        this.balance = event.getOpeningBalance();
    }

    @CommandHandler
    public void handle(DepositFundsCommand command){
        var amount = command.getAmount();
        FundsDepositedEvent event = FundsDepositedEvent
                .builder()
                .id(command.getId())
                .amount(amount)
                .balance(amount+this.balance)
                .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(FundsDepositedEvent event){
        this.balance+=event.getAmount();
    }
    
    @CommandHandler
    public void handle(WithdrawFundsCommand command){
        var amount = command.getAmount();



        FundsWithdrawnEvent event = FundsWithdrawnEvent
                .builder()
                .id(command.getId())
                .balance(this.balance-amount)
                .amount(amount)
                .build();
        AggregateLifecycle.apply(event);
    }


    @EventSourcingHandler
    public void on(FundsWithdrawnEvent event){

        this.balance-=event.getBalance();
    }

    @CommandHandler
    public void handle(CloseAccountCommand command){
        AccountClosedEvent event = AccountClosedEvent
                .builder()
                .id(command.getId())
                .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AccountClosedEvent event){
        AggregateLifecycle.markDeleted();
    }
}

