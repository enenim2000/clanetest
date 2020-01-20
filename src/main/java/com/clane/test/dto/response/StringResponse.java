package com.clane.test.dto.response;

import com.clane.test.util.MessageUtil;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class StringResponse {

    private String message;

    private String data;

    public StringResponse(String data, String message) {
        this.data = data;
        this.message = MessageUtil.msg(message);

    }
}