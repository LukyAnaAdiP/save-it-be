package com.enigma.group5.save_it_backend.service;

import com.enigma.group5.save_it_backend.dto.request.PaymentRequest;
import com.enigma.group5.save_it_backend.entity.Payment;
import com.enigma.group5.save_it_backend.entity.Transaction;

public interface PaymentService {
    Payment createPayment(Transaction transaction);
}
