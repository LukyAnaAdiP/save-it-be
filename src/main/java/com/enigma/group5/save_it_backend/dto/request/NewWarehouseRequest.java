package com.enigma.group5.save_it_backend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewWarehouseRequest {

    @NotBlank(message = "Vendor ID is Required")
    private String vendorId;

    @NotBlank(message = "Transaction Detail ID is Required")
    private String transDetail;

    @NotNull(message = "Price is Required")
    @Min(value = 0, message = "Price Must be Greather Than or Equal 0")
    private Long price;

    @NotNull(message = "Stock is Required")
    @Min(value = 0, message = "Stock Must be Greather Than or Equal 0")
    private Integer stock;
}
