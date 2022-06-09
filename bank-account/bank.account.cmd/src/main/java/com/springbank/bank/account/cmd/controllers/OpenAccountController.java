package com.springbank.bank.account.cmd.controllers;

import com.springbank.bank.account.cmd.commands.OpenAccountCommand;
import com.springbank.bank.account.cmd.dto.OpenAccountResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/openBankAccount")
public class OpenAccountController {

    private final CommandGateway commandGateway;

    public OpenAccountController(CommandGateway commandGateway){
        this.commandGateway=commandGateway;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<OpenAccountResponse> openAccount(@Valid @RequestBody OpenAccountCommand command){
        String id = UUID.randomUUID().toString();
        try{
            command.setId(id);
            commandGateway.send(command);
            return new ResponseEntity<>(new OpenAccountResponse("successfully opened a new bank account",id),
                    HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<>(new OpenAccountResponse(
                    "error while processing request to open a new bank account ", id),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
