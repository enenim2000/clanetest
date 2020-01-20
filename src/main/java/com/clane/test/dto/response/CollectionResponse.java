package com.clane.test.dto.response;

import com.clane.test.util.MessageUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.catalina.util.RequestUtil;

import java.util.Collection;

@Getter
@Setter
@ToString
public class CollectionResponse<T> {

    private String message;

    private Collection<T> data;

    public CollectionResponse(Collection<T> result, String messageKey) {
        setMessage(MessageUtil.msg("success_response"));
        setData(result);
    }
}