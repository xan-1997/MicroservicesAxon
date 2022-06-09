package com.springbank.bank.account.cmd.dto;

import com.springbank.bank.account.core.dto.BaseResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenAccountResponse extends BaseResponse {
    private String id;

    public OpenAccountResponse(String message, String id){
        super(message);
        this.id=id;
    }
}
