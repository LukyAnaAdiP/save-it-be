package com.enigma.group5.save_it_backend.repository;

import com.enigma.group5.save_it_backend.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, String>, JpaSpecificationExecutor<Report> {

    List<Report> findByTransDateBetween(Date startDate, Date endDate);
}