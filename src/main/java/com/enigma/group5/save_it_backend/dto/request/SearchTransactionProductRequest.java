package com.enigma.group5.save_it_backend.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchTransactionProductRequest {
    private Integer page;
    private Integer size;
    private String sortBy;
    private String direction;
    private String username;
    private String price;
    private String stocks;
    private String createdAt;
}
