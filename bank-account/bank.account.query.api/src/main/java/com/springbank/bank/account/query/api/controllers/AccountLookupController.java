package com.springbank.bank.account.query.api.controllers;

import com.springbank.bank.account.query.api.dto.AccountLookupResponse;
import com.springbank.bank.account.query.api.dto.EqualityType;
import com.springbank.bank.account.query.api.queries.FindAccountByHolderIdQuery;
import com.springbank.bank.account.query.api.queries.FindAccountByIdQuery;
import com.springbank.bank.account.query.api.queries.FindAccountsWithBalanceQuery;
import com.springbank.bank.account.query.api.queries.FindAllAccountsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accountLookup")
public class AccountLookupController {

    private final QueryGateway gateway;

    public AccountLookupController(QueryGateway gateway){
        this.gateway= gateway;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookupResponse> getAllAccounts(){
        try {
            var query =  new FindAllAccountsQuery();
            var response =
                    gateway.query(query, ResponseTypes.instanceOf(AccountLookupResponse.class)).join();
            return response==null||response.getAccounts()==null?
                    new ResponseEntity<>(null, HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new AccountLookupResponse("Failed complete get all request!!!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable String id){
        try {
            var query =  new FindAccountByIdQuery(id);
            var response =
                    gateway.query(query, ResponseTypes.instanceOf(AccountLookupResponse.class)).join();
            return response==null||response.getAccounts()==null?
                    new ResponseEntity<>(null, HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(new AccountLookupResponse("Failed complete get by id request!!!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byHolderId/{id}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookupResponse> getAccountByHolderId(@PathVariable String id){
        try {
            var query =  new FindAccountByHolderIdQuery(id);
            var response =
                    gateway.query(query, ResponseTypes.instanceOf(AccountLookupResponse.class)).join();
            return response==null||response.getAccounts()==null?
                    new ResponseEntity<>(null, HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new AccountLookupResponse("Failed complete get by holder id request!!!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byBalance/{equalityType}/{balance}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<AccountLookupResponse> getAccountByBalance(@PathVariable EqualityType equalityType,
                                                                     @PathVariable double balance){
        try {
            var query =  new FindAccountsWithBalanceQuery(equalityType, balance);
            var response =
                    gateway.query(query, ResponseTypes.instanceOf(AccountLookupResponse.class)).join();
            return response==null||response.getAccounts()==null?
                    new ResponseEntity<>(null, HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new AccountLookupResponse("Failed complete get by holder id request!!!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
