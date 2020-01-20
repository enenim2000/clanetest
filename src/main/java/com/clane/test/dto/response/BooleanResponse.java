package com.clane.test.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BooleanResponse{

    private String message;

    private Boolean data;

    public BooleanResponse(Object data, String message) {
        setMessage(message);
        setData((Boolean) data);
    }
}