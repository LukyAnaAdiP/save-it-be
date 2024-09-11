package com.enigma.group5.save_it_backend.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WarehouseDeleteRequest {
    private String type;
    private String warehouseId;
}
