package com.enigma.group5.save_it_backend.dto.response;

import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WarehouseResponse {
    private String username;
    private String customerEmail;
    private String type;
    private List<WarehouseGoodsResponse> goods;
    private Date createdAt;
    private Date updatedAt;
}
