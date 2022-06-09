package com.springbank.user.oauth20.services;

import com.springbank.user.core.api.models.User;
import com.springbank.user.oauth20.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userService")
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    public UserService(UserRepository repository){
        this.repository = repository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByUsername(username);
        if (user.isEmpty()) throw new UsernameNotFoundException("Incorrect username or password!!!");

        var account = user.get().getAccount();

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(username)
                .password(account.getPassword())
                .authorities(account.getRoles())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
