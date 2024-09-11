package com.enigma.group5.save_it_backend.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewVendorRequest {
    @NotBlank(message = "Name is Required")
    @Column(name = "name")
    private String name;

    @Pattern(regexp = "^08\\d{9,11}$", message = "The phone number must be valid and start with '08' followed by 9 to 11 digits")
    @NotBlank(message = "Phone Number is Required")
    @Column(name = "mobile_phone_no")
    private String mobilePhoneNo;

    @NotBlank(message = "Address is Required")
    @Column(name = "address")
    private String address;

    @Email(message = "Email Format Not Valid")
    @Pattern(regexp = "^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$" , message = "The email is not in the correct format")
    @Column(name = "email")
    private String email;
}
