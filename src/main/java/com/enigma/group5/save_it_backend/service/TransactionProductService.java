package com.enigma.group5.save_it_backend.service;

import com.enigma.group5.save_it_backend.dto.request.TransactionProductUpdateRequest;
import com.enigma.group5.save_it_backend.dto.response.TransactionProductResponse;
import com.enigma.group5.save_it_backend.entity.Customer;
import com.enigma.group5.save_it_backend.entity.Transaction;
import com.enigma.group5.save_it_backend.dto.request.SearchTransactionProductRequest;
import com.enigma.group5.save_it_backend.entity.TransactionProduct;
import com.enigma.group5.save_it_backend.entity.Warehouse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionProductService {
    List<TransactionProduct> create(Transaction transactionRequest, List<Transaction> transactionsOccurred);
    TransactionProduct findByVendorProductId(String vpId);
    TransactionProduct getById(String id);
    List<TransactionProduct> getByTransactionId(String transactionId);
    Page<TransactionProductResponse> getAll(SearchTransactionProductRequest searchTransactionProductRequest);
    Page<TransactionProductResponse> getAllByCustomer(SearchTransactionProductRequest searchTransactionProductRequest);
    List<TransactionProduct> getAllByTransaction(List<Transaction> transactions);
    List<TransactionProduct> getAllByWarehouse(Warehouse warehouse);
    TransactionProductResponse update(TransactionProductUpdateRequest transactionProductUpdateRequest);
    Long countByCustomer(Customer customer);
    void deleteById(String id);
}
