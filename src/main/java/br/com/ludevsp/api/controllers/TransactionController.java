package br.com.ludevsp.api.controllers;

import br.com.ludevsp.api.dto.ApiResponse;
import br.com.ludevsp.api.dto.TransactionQuery;
import br.com.ludevsp.api.dto.TransactionRequest;
import br.com.ludevsp.api.dto.TransactionResponse;
import br.com.ludevsp.domain.intefaces.usecase.TransactionUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionUseCase transactionUseCase;

    public TransactionController(TransactionUseCase transition) {
        this.transactionUseCase = transition;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<TransactionResponse>> createTransaction(@Valid @RequestBody TransactionRequest request, BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }

        var transaction = transactionUseCase.execute(request.ToEntity());
        return new ResponseEntity<>(new ApiResponse<>(new TransactionResponse(transaction)), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getTransaction(@ModelAttribute TransactionQuery transactionQuery) {
        var transactions = transactionUseCase.getAllTransactions(transactionQuery).stream().map(TransactionResponse::new).toList();
        return ResponseEntity.ok(new ApiResponse<>(transactions));
    }
}
