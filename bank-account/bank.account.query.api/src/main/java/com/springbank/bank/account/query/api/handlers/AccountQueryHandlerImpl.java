package com.springbank.bank.account.query.api.handlers;

import com.springbank.bank.account.core.models.BankAccount;
import com.springbank.bank.account.query.api.dto.AccountLookupResponse;
import com.springbank.bank.account.query.api.dto.EqualityType;
import com.springbank.bank.account.query.api.queries.FindAccountByHolderIdQuery;
import com.springbank.bank.account.query.api.queries.FindAccountByIdQuery;
import com.springbank.bank.account.query.api.queries.FindAccountsWithBalanceQuery;
import com.springbank.bank.account.query.api.queries.FindAllAccountsQuery;
import com.springbank.bank.account.query.api.repositories.AccountRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountQueryHandlerImpl implements AccountQueryHandler {

    private final AccountRepository repository;

    public AccountQueryHandlerImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAccountById(FindAccountByIdQuery query) {
        BankAccount account = repository.findById(query.getId())
                .orElseThrow(() -> new NullPointerException("There is no account with this id"));
        System.out.println(account.getId()+"    ghghghgh");
        return new AccountLookupResponse("Bank account successfully returned!!!", account);
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAccountByHolderId(FindAccountByHolderIdQuery query) {
        BankAccount account = repository.findById(query.getAccountHolderId())
                .orElseThrow(() -> new NullPointerException("There is no account with this account holder id"));
        return new AccountLookupResponse("Bank account successfully returned!!!", account);
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAllAccounts(FindAllAccountsQuery query) {
        List<BankAccount> accounts = repository.findAll();
        return accounts.isEmpty() ?
                new AccountLookupResponse("No Bank Accounts were found!!!") :
                new AccountLookupResponse("Successfully returned " + accounts.size() + " accounts!!!", accounts);
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAccountWithBalance(FindAccountsWithBalanceQuery query) {
        List<BankAccount> accounts = query.getEqualityType() == EqualityType.GREATER_THAN ?
                repository.findByBalanceGreaterThan(query.getBalance()) :
                repository.findByBalanceLessThan(query.getBalance());
        return accounts.isEmpty() ?
                new AccountLookupResponse("Successfully returned " + accounts.size() + " accounts!!!", accounts) :
                new AccountLookupResponse("No Bank Accounts were found!!! ");
    }
}
