package com.enigma.group5.save_it_backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.junit.jupiter.api.BeforeAll;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentTransactionDetailRequest {
    @JsonProperty("gross_amount")
    private Long amount;

    @JsonProperty("order_id")
    private String orderId;
}
