package com.springbank.bank.account.query.api.queries;

import com.springbank.bank.account.query.api.dto.EqualityType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountsWithBalanceQuery {
    private EqualityType equalityType;
    private double balance;
}
