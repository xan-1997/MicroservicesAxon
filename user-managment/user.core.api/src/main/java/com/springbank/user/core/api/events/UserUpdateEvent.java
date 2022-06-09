package com.springbank.user.core.api.events;

import com.springbank.user.core.api.models.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateEvent {
    private String id;
    private User user;
}
