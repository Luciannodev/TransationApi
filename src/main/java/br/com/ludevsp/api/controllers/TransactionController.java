package br.com.ludevsp.api.controllers;

import br.com.ludevsp.api.dto.TransactionRequest;
import br.com.ludevsp.api.dto.TransactionResponse;
import br.com.ludevsp.domain.intefaces.usecase.TransactionUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
