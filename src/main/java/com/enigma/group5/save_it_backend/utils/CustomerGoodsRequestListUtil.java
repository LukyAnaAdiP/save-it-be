package com.enigma.group5.save_it_backend.utils;

import com.enigma.group5.save_it_backend.dto.request.CustomerGoodsRequest;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerGoodsRequestListUtil {

    private List<CustomerGoodsRequest> customerGoodsRequests;

}
