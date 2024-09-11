package com.enigma.group5.save_it_backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {
    @JsonProperty("transaction_details")
    private PaymentTransactionDetailRequest paymentTransactionDetail;

    @JsonProperty("item_details")
    private List<PaymentItemDetailRequest> paymentItemDetail;

    @JsonProperty("enabled_payments")
    private List<String> paymentMethods;

    @JsonProperty("customer_details")
    private PaymentCustomerDetailRequest paymentCustomerDetail;
}
