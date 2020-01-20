package com.clane.test.dto.response;

import com.clane.test.util.ErrorModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorResponse {

    @JsonProperty("error_code")
    private int errorCode;

    @JsonProperty("error_message")
    private String errorMessage;

    private List<ErrorModel> errors;
}
