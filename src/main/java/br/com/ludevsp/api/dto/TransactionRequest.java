package br.com.ludevsp.api.dto;

import br.com.ludevsp.domain.entities.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionRequest {
    @NotEmpty(message = "Account code cannot be empty")
    @JsonProperty("account")
    private String accountCode;
    @Min(value = 0, message = "Total amount must be greater than 0")
    @JsonProperty("totalAmount")
    private BigDecimal totalAmount;
    @JsonProperty("mcc")
    private String mmc;
    @NotEmpty(message = "Merchant name cannot be empty")
    @JsonProperty("merchant")
    private String MerchantName;

    public Transaction ToEntity() {
        return new Transaction(accountCode,totalAmount, mmc, MerchantName);
    }
}
