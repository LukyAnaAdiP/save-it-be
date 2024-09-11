package com.enigma.group5.save_it_backend.dto.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionProductResponse {
    private String userName;
    private String customerEmail;
    private TransactionProductsGoodsResponse goodsDetails;
    private Long price;
    private Integer stocks;
    private Date createdDate;
    private Date updatedDate;
}
