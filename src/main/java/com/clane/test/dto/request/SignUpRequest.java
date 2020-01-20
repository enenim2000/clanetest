package com.clane.test.dto.request;

import com.clane.test.model.Author;
import com.clane.test.validator.IsUnique;
import com.clane.test.validator.PasswordMatch;
import com.clane.test.validator.ValidEmail;
import com.clane.test.validator.ValidPassword;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatch(message = "confirm_password_match")
public class SignUpRequest {

    @NotBlank(message = "author_name_required")
    private String name;

    @NotBlank(message = "author_email_required")
    @IsUnique(entity = Author.class, fieldName = "email", message = "author_email_exist")
    @ValidEmail(message = "email_format_invalid")
    private String email;

    @NotBlank(message = "author_password_required")
    @ValidPassword(message = "password_strength_invalid")
    private String password;

    @NotBlank(message = "confirm_password_required")
    @JsonProperty("confirm_password")
    private String confirmPassword;

    @NotBlank(message = "author_bio_required")
    private String bio;
}
