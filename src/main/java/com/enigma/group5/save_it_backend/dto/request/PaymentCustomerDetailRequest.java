package com.enigma.group5.save_it_backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentCustomerDetailRequest {
    @JsonProperty("first_name")
    private String firstName;
}
