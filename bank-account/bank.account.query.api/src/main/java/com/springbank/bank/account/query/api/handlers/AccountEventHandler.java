package com.springbank.bank.account.query.api.handlers;

import com.springbank.bank.account.core.events.AccountClosedEvent;
import com.springbank.bank.account.core.events.AccountOpenedEvent;
import com.springbank.bank.account.core.events.FundsDepositedEvent;
import com.springbank.bank.account.core.events.FundsWithdrawnEvent;

public interface AccountEventHandler {
    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawnEvent event);
    void on(AccountClosedEvent event);
}
