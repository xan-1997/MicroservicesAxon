package com.springbank.user.core.api.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRemoveEvent {
    private String id;
}
