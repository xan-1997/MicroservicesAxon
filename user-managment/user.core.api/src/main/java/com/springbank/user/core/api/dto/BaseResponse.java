package com.springbank.user.core.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseResponse {
    private String message;

    public BaseResponse(String message) {
        this.message = message;
    }
}
