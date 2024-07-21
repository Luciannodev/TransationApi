package br.com.ludevsp.api.controllers;

import br.com.ludevsp.domain.dto.ResponseCode;
import br.com.ludevsp.api.dto.TransationRequest;
import br.com.ludevsp.api.dto.TransationResponse;
import br.com.ludevsp.domain.intefaces.usecase.TransitionUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransationController {
    private TransitionUseCase transitionUseCase;

    public TransationController(TransitionUseCase transition) {
        this.transitionUseCase = transition;
    }

    @RequestMapping(value = "/create_transation", method = RequestMethod.POST)
    public ResponseEntity<TransationResponse> createTransaction(@RequestBody TransationRequest request) {
        var transation = transitionUseCase.execute(request.ToEntity());
        return ResponseEntity.ok(new TransationResponse(transation.getResponseCode()));
    }

}
