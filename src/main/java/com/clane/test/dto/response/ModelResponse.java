package com.clane.test.dto.response;

import com.clane.test.util.MessageUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ModelResponse<T> {

    private String message;

    private T data;

    public ModelResponse(T data, String messageKey) {
        setMessage(MessageUtil.msg(message));
        setData(data);
    }
}