package com.springbank.bank.account.core.events;

import com.springbank.bank.account.core.models.AccountType;
import com.springbank.bank.account.core.models.BankAccount;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AccountOpenedEvent {
    private String id;
    private String accountHolderId;
    private AccountType accountType;
    private Date creationDate;
    private double openingBalance;
}
