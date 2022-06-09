package com.springbank.user.query.api.controllers;

import com.springbank.user.query.api.dto.UserLookupResponse;
import com.springbank.user.query.api.queries.FindAllUsersQuery;
import com.springbank.user.query.api.queries.FindUserByIdQuery;
import com.springbank.user.query.api.queries.SearchUsersQuery;
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
@RequestMapping("/api/userLookup")
public class UserLookupController {

    private final QueryGateway queryGateway;

    public UserLookupController(QueryGateway queryGateway){
        this.queryGateway=queryGateway;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<UserLookupResponse> getAll(){
//        try {
            var query = new FindAllUsersQuery();
            var response = queryGateway.query(query, ResponseTypes.instanceOf(UserLookupResponse.class)).join();
            if (response == null || response.getUsers() == null)
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(response, HttpStatus.OK);
//        }catch (Exception e){
//            var message = "Failed to complete get all users!!!";
//            return new ResponseEntity<>(new UserLookupResponse(message), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<UserLookupResponse> getUserById(@PathVariable String id){
        try {
            var query = new FindUserByIdQuery(id);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(UserLookupResponse.class)).join();
            if (response == null || response.getUsers() == null)
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            var message = "Failed to complete get user by id!!!";
            return new ResponseEntity<>(new UserLookupResponse(message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/filter/{filter}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<UserLookupResponse> getUserByFilter(@PathVariable String filter){
        try {
            var query = new SearchUsersQuery(filter);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(UserLookupResponse.class)).join();
            if (response == null || response.getUsers() == null)
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            var message = "Failed to complete search user by filter!!!";
            return new ResponseEntity<>(new UserLookupResponse(message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
