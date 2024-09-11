package com.enigma.group5.save_it_backend.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePaymentStatusRequest {
    private String orderId;
    private String status;
    private String fraudStatus;
}
