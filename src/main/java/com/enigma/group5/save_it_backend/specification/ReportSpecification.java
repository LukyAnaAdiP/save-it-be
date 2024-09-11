package com.enigma.group5.save_it_backend.specification;

import com.enigma.group5.save_it_backend.dto.request.SearchReportRequest;
import com.enigma.group5.save_it_backend.entity.Report;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReportSpecification {

    public static Specification<Report> getSpecification(SearchReportRequest searchReportRequest) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (searchReportRequest.getTransDate() != null) {

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate parseDate = LocalDate.parse(searchReportRequest.getTransDate(), dateTimeFormatter).atStartOfDay().toLocalDate();
                Predicate birthdatePred = criteriaBuilder.equal(root.get("transDate"), parseDate);

                predicates.add(birthdatePred);
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
