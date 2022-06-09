package com.springbank.user.query.api.dto;

import com.springbank.user.core.api.dto.BaseResponse;
import com.springbank.user.core.api.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class UserLookupResponse extends BaseResponse {
    private List<User> users;


    public UserLookupResponse(String message){
        super(message);
    }
    public UserLookupResponse(User user){
        if (users==null) users = new ArrayList<>();
        users.add(user);
    }
}
