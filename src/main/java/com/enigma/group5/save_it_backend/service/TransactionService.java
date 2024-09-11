package com.enigma.group5.save_it_backend.service;

import com.enigma.group5.save_it_backend.dto.request.SearchTransactionRequest;
import com.enigma.group5.save_it_backend.dto.request.TransactionRequest;
import com.enigma.group5.save_it_backend.dto.request.UpdatePaymentStatusRequest;
import com.enigma.group5.save_it_backend.dto.response.TransactionResponse;
import com.enigma.group5.save_it_backend.entity.Customer;
import com.enigma.group5.save_it_backend.entity.Transaction;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionService {
    TransactionResponse create(TransactionRequest request);
    Page<TransactionResponse> getAll(SearchTransactionRequest searchTransactionRequest);
    List<Transaction> getAllByCustomer(Customer customer);
    void updateStatus(UpdatePaymentStatusRequest request);
}
