package com.springbank.bank.account.query.api.handlers;

import com.springbank.bank.account.core.events.AccountClosedEvent;
import com.springbank.bank.account.core.events.AccountOpenedEvent;
import com.springbank.bank.account.core.events.FundsDepositedEvent;
import com.springbank.bank.account.core.events.FundsWithdrawnEvent;
import com.springbank.bank.account.core.models.BankAccount;
import com.springbank.bank.account.query.api.repositories.AccountRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@ProcessingGroup("bank-account")
public class AccountEventHandlerImpl implements AccountEventHandler {

    private final AccountRepository repository;

    public AccountEventHandlerImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    @Override
    public void on(AccountOpenedEvent event) {
        BankAccount account = BankAccount
                .builder()
                .id(event.getId())
                .accountHolderId(event.getAccountHolderId())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .date(new Date())
                .build();
        repository.save(account);
    }

    @EventHandler
    @Override
    public void on(FundsDepositedEvent event) {
        System.out.println("in query api!!!");
        BankAccount account =
                repository.findById(event.getId())
                        .orElseThrow(() -> new NullPointerException("There is no account with this id!!!"));
        account.setBalance(account.getBalance()+ event.getAmount());
        repository.save(account);
    }

    @EventHandler
    @Override
    public void on(FundsWithdrawnEvent event) {
        BankAccount account =
                repository.findById(event.getId())
                        .orElseThrow(() -> new NullPointerException("There is no account with this id!!!"));
        if (account.getBalance()<event.getAmount()) throw new IllegalStateException();
        account.setBalance(account.getBalance()-event.getAmount());
        repository.save(account);
    }

    @EventHandler
    @Override
    public void on(AccountClosedEvent event) {
        repository.deleteById(event.getId());
    }
}
