package br.com.ludevsp.api.dto;

import br.com.ludevsp.domain.entities.Transation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransationRequest {
    @JsonProperty("account")
    private String accountCode;
    @JsonProperty("totalAmount")
    private BigDecimal totalAmount;
    @JsonProperty("mcc")
    private String mmc;
    @JsonProperty("merchant")
    private String MerchantName;

    public Transation ToEntity() {
        return new Transation(accountCode, totalAmount, mmc, MerchantName);
    }
}
