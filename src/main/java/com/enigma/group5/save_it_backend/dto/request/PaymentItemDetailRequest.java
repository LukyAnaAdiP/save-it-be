package com.enigma.group5.save_it_backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentItemDetailRequest {
    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("price")
    private Long price;

    @JsonProperty("name")
    private String name;

    @JsonProperty("category")
    private String category;
}
