package com.enigma.group5.save_it_backend.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendorResponse {
    String vendorId;
    String vendorName;
    String vendorEmail;
    String vendorPhone;
    String vendorAddress;
}
