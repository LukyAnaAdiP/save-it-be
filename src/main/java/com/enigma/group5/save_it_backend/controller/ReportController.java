package com.enigma.group5.save_it_backend.controller;

import com.enigma.group5.save_it_backend.constant.APIUrl;
import com.enigma.group5.save_it_backend.constant.ResponseMessage;
import com.enigma.group5.save_it_backend.dto.request.SearchReportRequest;
import com.enigma.group5.save_it_backend.dto.response.CommonResponse;
import com.enigma.group5.save_it_backend.dto.response.PagingResponse;
import com.enigma.group5.save_it_backend.dto.response.ReportResponse;
import com.enigma.group5.save_it_backend.entity.Report;
import com.enigma.group5.save_it_backend.service.ReportService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.REPORT_API)
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/daily")
    private ResponseEntity<CommonResponse<List<Report>>> getAllReport(
            @RequestParam(name = "transDate", required = false) @JsonFormat(pattern = "yyyy-MM-dd") String transDate,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "transDate") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction) {

        SearchReportRequest request = SearchReportRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .transDate(transDate)
                .build();

        Page<Report> reportList = reportService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(reportList.getTotalPages())
                .totalElements(reportList.getTotalElements())
                .page(reportList.getPageable().getPageNumber())
                .size(reportList.getPageable().getPageSize())
                .hasNext(reportList.hasNext())
                .hasPrevious(reportList.hasPrevious())
                .build();

        CommonResponse<List<Report>> response = CommonResponse.<List<Report>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(reportList.getContent())
                .paging(pagingResponse)
                .build();


        reportService.exportToCSV(reportList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/month")
    public ResponseEntity<CommonResponse<List<Report>>> getReportMonths(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "startDate") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction) {

        SearchReportRequest request = SearchReportRequest.builder()
                .startDate(startDate)
                .endDate(endDate)
                .page(page)
                .size(page)
                .sortBy(sortBy)
                .direction(direction)
                .build();
        List<Report> reports = reportService.getReportsBetweenDate(startDate, endDate);

        CommonResponse<List<Report>> response = CommonResponse.<List<Report>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(reports)
                .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/customer")
    public ResponseEntity<CommonResponse<List<ReportResponse>>> getAllReportBasedOnCustomer(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "transactionDate") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        SearchReportRequest request = SearchReportRequest.builder()
                .startDate(startDate)
                .endDate(endDate)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();

        Page<ReportResponse> reports = reportService.getAllReportBasedOnCustomer(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(reports.getTotalPages())
                .totalElements(reports.getTotalElements())
                .page(reports.getPageable().getPageNumber())
                .size(reports.getPageable().getPageSize())
                .hasNext(reports.hasNext())
                .hasPrevious(reports.hasPrevious())
                .build();

        CommonResponse<List<ReportResponse>> response = CommonResponse.<List<ReportResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(reports.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }
}