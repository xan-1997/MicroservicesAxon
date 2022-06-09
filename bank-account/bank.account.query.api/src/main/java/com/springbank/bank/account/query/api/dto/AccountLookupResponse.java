package com.springbank.bank.account.query.api.dto;

import com.springbank.bank.account.core.dto.BaseResponse;
import com.springbank.bank.account.core.models.BankAccount;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AccountLookupResponse extends BaseResponse {
    private List<BankAccount> accounts;

    public AccountLookupResponse(String message){
        super(message);
    }

    public AccountLookupResponse(String message, List<BankAccount> accounts){
        super(message);
        this.accounts=accounts;
    }

    public AccountLookupResponse(String message, BankAccount account){
        super(message);
        this.accounts=new ArrayList<>();
        accounts.add(account);
    }
}
