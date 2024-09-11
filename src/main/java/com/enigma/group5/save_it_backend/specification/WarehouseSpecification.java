package com.enigma.group5.save_it_backend.specification;

import com.enigma.group5.save_it_backend.dto.request.SearchTransactionProductRequest;
import com.enigma.group5.save_it_backend.dto.request.SearchWarehouseRequest;
import com.enigma.group5.save_it_backend.entity.TransactionProduct;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class WarehouseSpecification {
    public static Specification<TransactionProduct> getSpecification(SearchWarehouseRequest searchWarehouseRequest){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (searchWarehouseRequest.getCreatedAt() != null) {
                Predicate warehousePredicate = criteriaBuilder.equal(root.get("createdAt"),
                        searchWarehouseRequest.getCreatedAt());
                predicates.add(warehousePredicate);
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
