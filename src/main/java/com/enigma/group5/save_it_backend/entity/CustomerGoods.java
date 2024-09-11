package com.enigma.group5.save_it_backend.entity;

import com.enigma.group5.save_it_backend.constant.ConstantTable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = ConstantTable.CUSTOMER_GOODS)
public class CustomerGoods {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_account_id", nullable = false)
    private UserAccount userAccount;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "goods_id", nullable = false)
    private Goods goods;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    @JsonBackReference
    private Warehouse warehouse;

    @Min(value = 0, message = "price should be more than 0")
    @Column(name = "price")
    private Long price;

    @Min(value = 0, message = "stocks should be more than 0")
    @Column(name = "stocks")
    private Integer stocks;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false, nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    /*
     "customerId": "customerId",
     "goods": [
        {
         "goodsId": "goodsId",
         "goodsName": "goodsName",
         "goodsDescription": "goodsDescription",
         "price": "price",
         "stocks": "stocks",
         "goodsImageId": "goodsImageId" (but this is multipart file)
         }
     ]
    * */

}
