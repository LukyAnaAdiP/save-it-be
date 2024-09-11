package com.enigma.group5.save_it_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
    private List<TransactionDetailRequest> transactionDetail;
}
