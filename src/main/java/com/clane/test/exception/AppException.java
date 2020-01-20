package com.clane.test.exception;

import com.clane.test.util.MessageUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
public class AppException extends RuntimeException{

    private HttpStatus status;

    public AppException(String messageKey) {
        super(MessageUtil.msg(messageKey));
        status = HttpStatus.EXPECTATION_FAILED;
    }

    public AppException(String messageKey, HttpStatus status) {
        super(MessageUtil.msg(messageKey));
        this.status = status;
    }

    public AppException(String messageKey, String placeHolder, HttpStatus status) {
        super(MessageUtil.msg(messageKey).replace("{}", placeHolder));
        this.status = status;
    }
}