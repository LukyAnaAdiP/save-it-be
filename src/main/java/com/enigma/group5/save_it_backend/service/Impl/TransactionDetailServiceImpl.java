package com.enigma.group5.save_it_backend.service.Impl;

import com.enigma.group5.save_it_backend.entity.TransactionDetail;
import com.enigma.group5.save_it_backend.repository.TransactionDetailsRepository;
import com.enigma.group5.save_it_backend.service.TransactionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionDetailServiceImpl implements TransactionDetailService {

    private final TransactionDetailsRepository transactionDetailsRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<TransactionDetail> createBulk(List<TransactionDetail> transactionDetails) {
        return transactionDetailsRepository.saveAllAndFlush(transactionDetails);
    }

}
