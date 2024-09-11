package com.enigma.group5.save_it_backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.stereotype.Service;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank(message = "username is Required")
    private String username;

    @NotBlank(message = "email is Required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "password is Required")
    private String password;
}
