package com.enigma.group5.save_it_backend.service;


import com.enigma.group5.save_it_backend.dto.request.SearchReportRequest;
import com.enigma.group5.save_it_backend.dto.response.ReportResponse;
import com.enigma.group5.save_it_backend.entity.Report;
import com.enigma.group5.save_it_backend.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReportService {

    List<Report> createReport(Transaction transaction);
    Page<Report> getAll(SearchReportRequest searchReportRequest);
    Page<ReportResponse> getAllReportBasedOnCustomer(SearchReportRequest searchReportRequest);
    List<Report> getReportsBetweenDate(Date startDate, Date endDate);
    // For export to csv
    void exportToCSV(Page<Report> reports);
}
