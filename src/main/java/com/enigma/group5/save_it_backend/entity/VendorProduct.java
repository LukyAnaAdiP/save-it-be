package com.enigma.group5.save_it_backend.entity;

import com.enigma.group5.save_it_backend.constant.ConstantTable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = ConstantTable.VENDOR_PRODUCT)
public class VendorProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Min(value = 0, message = "price should be more than 0")
    @Column(name = "price", nullable = false)
    private Long price;

    @Min(value = 0, message = "stocks should be more than 0")
    @Column(name = "stocks", nullable = false)
    private Integer stocks;

}
