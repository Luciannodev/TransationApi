package br.com.ludevsp.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransationResponse {
    @JsonProperty("code")
    private String code;

    public TransationResponse(String code) {
        this.code = code;
    }
}
