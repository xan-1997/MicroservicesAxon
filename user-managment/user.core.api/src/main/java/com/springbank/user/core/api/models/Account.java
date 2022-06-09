package com.springbank.user.core.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Document
public class Account {
    @Size(min = 2, message = "username must have minimum 2 characters")
    private String username;
    @Size(min = 7, message = "password must have minimum 7 characters")
    private String password;
    @NotNull(message = "specify at least 1 user role")
    private Set<Role> roles;

}
