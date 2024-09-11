package com.enigma.group5.save_it_backend.dto.response;

import com.enigma.group5.save_it_backend.dto.request.GoodsRequest;
import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerGoodsResponse<T> {
    private String username;
    private String customerEmail;
    private T goods;
    private Long price;
    private Integer stocks;
    private Date createdDate;
    private Date updatedDate;
}
