package com.enigma.group5.save_it_backend.entity;

import com.enigma.group5.save_it_backend.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = ConstantTable.VENDOR)
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "vendor_email")
    private String vendorEmail;

    @Column(name = "vendor_phone")
    private String vendorPhone;

    @Column(name = "vendor_address")
    private String vendorAddress;

}
