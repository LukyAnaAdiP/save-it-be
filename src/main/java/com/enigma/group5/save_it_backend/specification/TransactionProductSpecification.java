package com.enigma.group5.save_it_backend.specification;

import com.enigma.group5.save_it_backend.dto.request.SearchTransactionProductRequest;
import com.enigma.group5.save_it_backend.entity.TransactionProduct;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TransactionProductSpecification {
    public static Specification<TransactionProduct> getSpecification(SearchTransactionProductRequest searchTransactionProductRequest) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (searchTransactionProductRequest.getCreatedAt() != null) {
                Predicate customerGoodsPredicate = criteriaBuilder.equal(root.get("createdAt"),
                        searchTransactionProductRequest.getCreatedAt());
                predicates.add(customerGoodsPredicate);
            }
            if (searchTransactionProductRequest.getPrice() != null) {
                Predicate customerGoodsPredicate = criteriaBuilder.equal(root.get("price"),
                        searchTransactionProductRequest.getPrice());
                predicates.add(customerGoodsPredicate);
            }
            if (searchTransactionProductRequest.getStocks() != null) {
                Predicate customerGoodsPredicate = criteriaBuilder.equal(root.get("stock"),
                        searchTransactionProductRequest.getStocks());
                predicates.add(customerGoodsPredicate);
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
