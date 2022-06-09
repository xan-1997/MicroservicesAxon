package com.springbank.user.cmd.api.aggregates;

import com.springbank.user.cmd.api.commands.RegisterUserCommand;
import com.springbank.user.cmd.api.commands.RemoveUserCommand;
import com.springbank.user.cmd.api.commands.UpdateUserCommand;
import com.springbank.user.cmd.api.security.PasswordEncoder;
import com.springbank.user.cmd.api.security.PasswordEncoderImpl;
import com.springbank.user.core.api.events.UserRegisterEvent;
import com.springbank.user.core.api.events.UserRemoveEvent;
import com.springbank.user.core.api.events.UserUpdateEvent;
import com.springbank.user.core.api.models.Account;
import com.springbank.user.core.api.models.User;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class UserAggregate {
    @AggregateIdentifier
    private String id;
    private User user;
    private final PasswordEncoder encoder;

    public UserAggregate() {
        encoder = new PasswordEncoderImpl();
    }

    @CommandHandler
    public UserAggregate(RegisterUserCommand command) {
        encoder = new PasswordEncoderImpl();
        User user = command.getUser();
        Account account = user.getAccount();
        account.setPassword(encoder.hashPassword(account.getPassword()));
        user.setId(command.getId());
        UserRegisterEvent event = UserRegisterEvent
                .builder()
                .id(command.getId())
                .user(user)
                .build();
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateUserCommand command) {
        User user = command.getUser();
        user.setId(command.getId());
        Account account = user.getAccount();
        account.setPassword(encoder.hashPassword(account.getPassword()));

        UserUpdateEvent event = UserUpdateEvent
                .builder()
                .id(UUID.randomUUID().toString())
                .user(user)
                .build();
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(RemoveUserCommand command) {
        UserRemoveEvent event = UserRemoveEvent
                .builder()
                .id(command.getId())
                .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(UserRegisterEvent event) {
        this.id = event.getId( );
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void on(UserUpdateEvent event) {
        this.user=event.getUser();
    }

    @EventSourcingHandler
    public void on(UserRemoveEvent event) {
        this.id = event.getId();
        this.user = User.builder().id(event.getId()).build();
//        AggregateLifecycle.markDeleted();
    }
}

