package com.springbank.bank.account.cmd.controllers;

import com.springbank.bank.account.cmd.commands.DepositFundsCommand;
import com.springbank.bank.account.core.dto.BaseResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/depositFunds")
public class DepositFundsController {

    private final CommandGateway commandGateway;

    public DepositFundsController(CommandGateway commandGateway){
        this.commandGateway=commandGateway;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable String id,
                                                     @RequestBody DepositFundsCommand command){
        try {
            command.setId(id);
            commandGateway.send(command);
            return new ResponseEntity<>(new BaseResponse("Funds successfully deposited"),HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<>
                    (new BaseResponse("Error while processing request to deposit funds into bank account"),
                     HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
