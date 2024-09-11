package com.enigma.group5.save_it_backend.specification;

import com.enigma.group5.save_it_backend.dto.request.SearchVendorRequest;
import com.enigma.group5.save_it_backend.entity.Vendor;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class VendorSpecification {
    public static Specification<Vendor> getSpecification(SearchVendorRequest request){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getName() != null){
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("vendorName")),
                                "%" + request.getName().toLowerCase() + "%"
                        )
                );
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
