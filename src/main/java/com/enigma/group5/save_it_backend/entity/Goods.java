package com.enigma.group5.save_it_backend.entity;

import com.enigma.group5.save_it_backend.constant.ConstantTable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = ConstantTable.GOODS)
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "goods_image_id", unique = true)
    private Image goodsImage;

    @ManyToOne
    @JoinColumn(name = "goods_category_id", nullable = false)
    private GoodsCategory goodsCategory;

    @Column(name = "goods_name")
    private String goodsName;

    @Column(name = "goods_description")
    private String goodsDescription;

}
