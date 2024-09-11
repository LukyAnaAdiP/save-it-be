package com.enigma.group5.save_it_backend.specification;

import com.enigma.group5.save_it_backend.dto.request.SearchCustomerGoodsRequest;
import com.enigma.group5.save_it_backend.dto.request.SearchTransactionProductRequest;
import com.enigma.group5.save_it_backend.entity.CustomerGoods;
import com.enigma.group5.save_it_backend.entity.TransactionProduct;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CustomerGoodsSpecification {
    public static Specification<CustomerGoods> getSpecification(SearchCustomerGoodsRequest searchCustomerGoodsRequest){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (searchCustomerGoodsRequest.getCreatedAt() != null) {
                Predicate customerGoodsPredicate = criteriaBuilder.equal(root.get("createdAt"),
                        searchCustomerGoodsRequest.getCreatedAt());
                predicates.add(customerGoodsPredicate);
            }
            if (searchCustomerGoodsRequest.getPrice() != null) {
                Predicate customerGoodsPredicate = criteriaBuilder.equal(root.get("price"),
                        searchCustomerGoodsRequest.getPrice());
                predicates.add(customerGoodsPredicate);
            }
            if (searchCustomerGoodsRequest.getStocks() != null) {
                Predicate customerGoodsPredicate = criteriaBuilder.equal(root.get("stock"),
                        searchCustomerGoodsRequest.getStocks());
                predicates.add(customerGoodsPredicate);
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
