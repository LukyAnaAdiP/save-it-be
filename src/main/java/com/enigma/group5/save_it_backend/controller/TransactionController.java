package com.enigma.group5.save_it_backend.controller;

import com.enigma.group5.save_it_backend.constant.APIUrl;
import com.enigma.group5.save_it_backend.constant.ResponseMessage;
import com.enigma.group5.save_it_backend.dto.request.SearchTransactionRequest;
import com.enigma.group5.save_it_backend.dto.request.TransactionRequest;
import com.enigma.group5.save_it_backend.dto.request.UpdatePaymentStatusRequest;
import com.enigma.group5.save_it_backend.dto.response.*;
import com.enigma.group5.save_it_backend.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.TRANSACTION)
public class TransactionController {

    private final TransactionService transactionService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<NewTransactionResponse<TransactionResponse>> createNewTransaction(@RequestBody TransactionRequest transactionRequest){

        TransactionResponse newTransaction = transactionService.create(transactionRequest);
        List<TransactionDetailResponse> newTransactionDetail = newTransaction.getTransactionDetailResponses();

        NewTransactionResponse<TransactionResponse> response = NewTransactionResponse.<TransactionResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully Transaction")
                .transactionId(newTransaction.getId())
                .username(newTransaction.getUsername())
                .email(newTransaction.getCustomerEmail())
                .transactionDate(newTransaction.getTransactionDate())
                .data(newTransactionDetail)
                .paymentResponse(newTransaction.getPaymentResponse())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<TransactionResponse>>> getAllTransaction(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "4") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "name", required = false) String name
    ){
        SearchTransactionRequest request = SearchTransactionRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .name(name)
                .build();

        Page<TransactionResponse> allTransactions = transactionService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(allTransactions.getTotalPages())
                .totalElements(allTransactions.getTotalElements())
                .page(allTransactions.getPageable().getPageNumber() + 1)
                .size(allTransactions.getPageable().getPageSize())
                .hasNext(allTransactions.hasNext())
                .hasPrevious(allTransactions.hasPrevious())
                .build();

        CommonResponse<List<TransactionResponse>> response = CommonResponse.<List<TransactionResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(allTransactions.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/status")
    public ResponseEntity<CommonResponse<?>> updateStatus (
            @RequestBody Map<String, Object> request
    ){
        UpdatePaymentStatusRequest updatePaymentStatusRequest = UpdatePaymentStatusRequest.builder()
                .orderId(request.get("order_id").toString())
                .status(request.get("transaction_status").toString())
                .fraudStatus(request.get("fraud_status").toString())
                .build();
        transactionService.updateStatus(updatePaymentStatusRequest);
        return ResponseEntity.ok(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success update payment status")
                        .build()
        );
    }
}
