package com.enigma.group5.save_it_backend.dto.request;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchReportRequest {
    private Integer page;
    private Integer size;
    private String sortBy;
    private String direction;
    private String transDate;
    private Date startDate;
    private Date endDate;
}
