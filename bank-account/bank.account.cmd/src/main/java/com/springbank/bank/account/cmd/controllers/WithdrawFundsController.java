package com.springbank.bank.account.cmd.controllers;

import com.springbank.bank.account.cmd.commands.WithdrawFundsCommand;
import com.springbank.bank.account.core.dto.BaseResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/withdrawFunds")
public class WithdrawFundsController {

    private final CommandGateway commandGateway;

    public WithdrawFundsController(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> withdrawFunds(@PathVariable String id,
                                                      @Valid @RequestBody WithdrawFundsCommand command){
        command.setId(id);
        try {
            commandGateway.send(command);
            return new ResponseEntity<>(new BaseResponse("Funds successfully withdrawn!!!"), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new BaseResponse("Error while processing request to withdraw funds into bank account"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
