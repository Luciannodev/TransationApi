package br.com.ludevsp.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransactionResponse {
    @JsonProperty("code")
    private String code;

    public TransactionResponse(String code) {
        this.code = code;
    }
}
