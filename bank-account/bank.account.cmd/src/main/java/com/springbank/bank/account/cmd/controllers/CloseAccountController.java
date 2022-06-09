package com.springbank.bank.account.cmd.controllers;

import com.springbank.bank.account.cmd.commands.CloseAccountCommand;
import com.springbank.bank.account.core.dto.BaseResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/closeAccount")
public class CloseAccountController {

    private final CommandGateway commandGateway;

    public CloseAccountController(CommandGateway commandGateway){
        this.commandGateway= commandGateway;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable String id){
        CloseAccountCommand command = CloseAccountCommand
                .builder()
                .id(id)
                .build();
        try {
            commandGateway.send(command);
            return new ResponseEntity<>(new BaseResponse("successfully close account!!!"), HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<>(new BaseResponse("error while processing request to close a new bank account"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
