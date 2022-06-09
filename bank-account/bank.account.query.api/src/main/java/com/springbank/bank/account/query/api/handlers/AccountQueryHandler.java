package com.springbank.bank.account.query.api.handlers;

import com.springbank.bank.account.query.api.dto.AccountLookupResponse;
import com.springbank.bank.account.query.api.queries.FindAccountByHolderIdQuery;
import com.springbank.bank.account.query.api.queries.FindAccountByIdQuery;
import com.springbank.bank.account.query.api.queries.FindAccountsWithBalanceQuery;
import com.springbank.bank.account.query.api.queries.FindAllAccountsQuery;

public interface AccountQueryHandler {
    AccountLookupResponse findAccountById(FindAccountByIdQuery query);
    AccountLookupResponse findAccountByHolderId(FindAccountByHolderIdQuery query);
    AccountLookupResponse findAllAccounts(FindAllAccountsQuery query);
    AccountLookupResponse findAccountWithBalance(FindAccountsWithBalanceQuery query);
}
