package com.springbank.bank.account.query.api.repositories;

import com.springbank.bank.account.core.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository
        extends JpaRepository<BankAccount, String>
{
    Optional<BankAccount> findByAccountHolderId (String accountHolderId);
    List<BankAccount> findByBalanceGreaterThan(double balance);
    List<BankAccount> findByBalanceLessThan(double balance);
}
