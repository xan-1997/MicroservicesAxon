package com.springbank.user.cmd.api.dto;

import lombok.Data;

@Data
public class UpdateUserResponse extends BaseResponse{
    private String id;

    public UpdateUserResponse(String message) {
        super(message);
    }
}
