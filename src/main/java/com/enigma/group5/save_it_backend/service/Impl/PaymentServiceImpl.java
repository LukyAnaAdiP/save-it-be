package com.enigma.group5.save_it_backend.service.Impl;

import com.enigma.group5.save_it_backend.constant.PaymentMethod;
import com.enigma.group5.save_it_backend.dto.request.PaymentCustomerDetailRequest;
import com.enigma.group5.save_it_backend.dto.request.PaymentItemDetailRequest;
import com.enigma.group5.save_it_backend.dto.request.PaymentRequest;
import com.enigma.group5.save_it_backend.dto.request.PaymentTransactionDetailRequest;
import com.enigma.group5.save_it_backend.entity.Payment;
import com.enigma.group5.save_it_backend.entity.Transaction;
import com.enigma.group5.save_it_backend.repository.PaymentRepository;
import com.enigma.group5.save_it_backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final RestClient restClient;
    private final String SECRET_KEY;
    private final String BASE_URL_SNAP;

    @Autowired
    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            RestClient restClient,
            @Value("${midtrans.api.key}") String SECRET_KEY,
            @Value("${midtrans.api.snap-url") String BASE_URL_SNAP
    ) {
        this.paymentRepository = paymentRepository;
        this.restClient = restClient;
        this.SECRET_KEY = SECRET_KEY;
        this.BASE_URL_SNAP = BASE_URL_SNAP;
    }

    @Transactional(readOnly = true)
    @Override
    public Payment createPayment(Transaction transaction) {

        long amount = transaction.getTransactionDetails().stream()
                .mapToLong(trx -> trx.getQuantity() * trx.getVendorProduct().getPrice())
                .reduce(0, Long::sum);

        PaymentTransactionDetailRequest paymentTransactionDetailRequest = PaymentTransactionDetailRequest.builder()
                .orderId(transaction.getId())
                .amount(amount)
                .build();

        List<PaymentItemDetailRequest> paymentItemDetailRequest = transaction.getTransactionDetails().stream().map(
                transactionDetail -> PaymentItemDetailRequest.builder()
                        .name(transactionDetail.getVendorProduct().getProduct().getProductName().substring(0,4))
                        .price(transactionDetail.getVendorProduct().getPrice())
                        .category(transactionDetail.getVendorProduct().getProduct().getProductCategory().getCategoryName())
                        .quantity(transactionDetail.getQuantity())
                        .build()
        ).toList();

        PaymentCustomerDetailRequest customerDetailRequest = PaymentCustomerDetailRequest.builder()
                .firstName(transaction.getCustomer().getFullNameCustomer() != null ? transaction.getCustomer().getFullNameCustomer() : "Guest")
                .build();

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .paymentTransactionDetail(paymentTransactionDetailRequest)
                .paymentItemDetail(paymentItemDetailRequest)
                .paymentCustomerDetail(customerDetailRequest)
                .paymentMethods(PaymentMethod.PAYMENT_METHODS)
                .build();

        ResponseEntity<Map<String, String>> response = restClient.post()
                .uri("https://app.sandbox.midtrans.com/snap/v1/transactions")
                .header("Authorization", "Basic " + SECRET_KEY)
                .body(paymentRequest)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});

        Map<String, String> body = response.getBody();

        if(body == null) {
            return null;
        }

        return paymentRepository.saveAndFlush(Payment.builder()
                        .token(body.get("token"))
                        .redirectUrl(body.get("redirect_url"))
                        .transactionStatus("awaiting payment")
                .build());
    }
}
