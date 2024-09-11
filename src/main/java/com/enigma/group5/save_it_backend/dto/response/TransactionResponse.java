package com.enigma.group5.save_it_backend.dto.response;

import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private String id;
    private String username;
    private String customerEmail;
    private Date transactionDate;
    private List<TransactionDetailResponse> transactionDetailResponses;
    private PaymentResponse paymentResponse;
}
