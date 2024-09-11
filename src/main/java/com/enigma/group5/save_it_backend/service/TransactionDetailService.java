package com.enigma.group5.save_it_backend.service;

import com.enigma.group5.save_it_backend.entity.TransactionDetail;

import java.util.List;

public interface TransactionDetailService {
    List<TransactionDetail> createBulk (List<TransactionDetail> transactionDetails);
}
