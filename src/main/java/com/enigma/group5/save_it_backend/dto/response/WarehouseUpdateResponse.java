package com.enigma.group5.save_it_backend.dto.response;

import lombok.*;

import java.util.Date;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WarehouseUpdateResponse<T> {
    private String username;
    private String customerEmail;
    private String type;
    private T goods;
    private Long price;
    private Integer stocks;
    private Date createdAt;
    private Date updatedAt;
}
