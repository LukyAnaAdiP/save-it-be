package com.enigma.group5.save_it_backend.dto.response;


import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportResponse {
    private String transactionId;
    private Date transactionDate;
    private String transactionTime;
    private String paymentToken;
    private String redirectUrl;
    private String paymentStatus;
    private String username;
    private String customerEmail;
    private List<ItemReportResponses> items;
    private Long totalPrice;
    private Integer totalQuantity;
}
