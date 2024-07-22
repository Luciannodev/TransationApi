package br.com.ludevsp.api.controllers;

import br.com.ludevsp.api.dto.TransactionQuery;
import br.com.ludevsp.api.dto.TransactionRequest;
import br.com.ludevsp.api.dto.TransactionResponse;
import br.com.ludevsp.domain.intefaces.usecase.TransactionUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {
    private final TransactionUseCase transactionUseCase;

    public TransactionController(TransactionUseCase transition) {
        this.transactionUseCase = transition;
    }

    @RequestMapping(value = "/create_transaction", method = RequestMethod.POST)
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest request) {
        var transation = transactionUseCase.execute(request.ToEntity());
        return ResponseEntity.ok(new TransactionResponse(transation.getResponseCode()));
    }
    @RequestMapping(value = "/get_transaction", method = RequestMethod.GET)
    public ResponseEntity<List<TransactionResponse>> getTransaction(@ModelAttribute TransactionQuery transactionQuery) {
        var transactions = transactionUseCase.getAllTransactions(transactionQuery).stream().map(TransactionResponse::new).toList();
        return ResponseEntity.ok(transactions);
    }
}
