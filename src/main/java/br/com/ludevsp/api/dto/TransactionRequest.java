package br.com.ludevsp.api.dto;

import br.com.ludevsp.domain.entities.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionRequest {
    @JsonProperty("account")
    private String accountCode;
    @JsonProperty("totalAmount")
    private BigDecimal totalAmount;
    @JsonProperty("mcc")
    private String mmc;
    @JsonProperty("merchant")
    private String MerchantName;

    public Transaction ToEntity() {
        return new Transaction(accountCode, totalAmount, mmc, MerchantName);
    }
}
