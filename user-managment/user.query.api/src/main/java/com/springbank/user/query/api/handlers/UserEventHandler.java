package com.springbank.user.query.api.handlers;

import com.springbank.user.core.api.events.UserRegisterEvent;
import com.springbank.user.core.api.events.UserRemoveEvent;
import com.springbank.user.core.api.events.UserUpdateEvent;

public interface UserEventHandler {
    void on(UserRegisterEvent event);
    void on(UserUpdateEvent event) throws Exception;
    void on(UserRemoveEvent event);

}
