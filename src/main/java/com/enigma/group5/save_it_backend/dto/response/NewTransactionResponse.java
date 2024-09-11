package com.enigma.group5.save_it_backend.dto.response;

import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewTransactionResponse<T> {
    private Integer statusCode;
    private String message;
    private String transactionId;
    private String username;
    private String email;
    private Date transactionDate;
    private List<TransactionDetailResponse> data;
    private PaymentResponse paymentResponse;
    private PagingResponse paging;
}
