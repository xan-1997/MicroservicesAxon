package com.springbank.user.query.api.handlers;

import com.springbank.user.core.api.events.UserRegisterEvent;
import com.springbank.user.core.api.events.UserRemoveEvent;
import com.springbank.user.core.api.events.UserUpdateEvent;
import com.springbank.user.query.api.repositories.UserRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("user-group")
public class UserEventHandlerImpl implements UserEventHandler {

    private final UserRepository repository;

    public UserEventHandlerImpl(UserRepository repository){
        this.repository=repository;
    }

    @EventHandler
    @Override
    public void on(UserRegisterEvent event) {
        System.out.println("in register event handler:query.api");
        repository.save(event.getUser());
    }

    @EventHandler
    @Override
    public void on(UserUpdateEvent event) {
        System.out.println("in update event handler:query.api");
        repository.save(event.getUser());
    }

    @EventHandler
    @Override
    public void on(UserRemoveEvent event) {
        System.out.println("in delete event handler:query.api");
        repository.deleteById(event.getId());
    }
}
