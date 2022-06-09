package com.springbank.user.cmd.api.controllers;

import com.springbank.user.cmd.api.commands.RegisterUserCommand;
import com.springbank.user.cmd.api.dto.RegisterUserResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/registerUser")
public class RegisterUserController {

    private final CommandGateway commandGateway;

    @Autowired
    public RegisterUserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public ResponseEntity<RegisterUserResponse> registerUser(@Valid @RequestBody RegisterUserCommand command){
        try {
            command.setId(UUID.randomUUID().toString());
            commandGateway.send(command);
            var response = new RegisterUserResponse("User successfully registered!!!");
            response.setId(command.getId());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (Exception e){
            String message = "Error processing user registration for user with id "+command.getId();
            System.out.println(e);
            return new ResponseEntity<>(new RegisterUserResponse(message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RegisterUserResponse> handleException(MethodArgumentNotValidException ex){
        BindingResult result = ex.getBindingResult();
        return new ResponseEntity<>(new RegisterUserResponse(result.getFieldError().getDefaultMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }




}
