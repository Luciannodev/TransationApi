package br.com.ludevsp.api.dto;

import br.com.ludevsp.domain.entities.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TransactionResponse {

    @JsonProperty("transactionCode")
    private long transactionCode;

    @JsonProperty("code")
    private String responseCode;

    @JsonProperty("create_time")
    private Timestamp createTime;

    @JsonProperty("merchantCode")
    private long merchantCode;

    @JsonProperty("totalAmount")
    private double totalAmount;

    @JsonProperty("accountCode")
    private long accountCode;

    @JsonProperty("mmc")
    private int mmc;

    public TransactionResponse(String responseCode) {
        this.responseCode = responseCode;
    }


    public TransactionResponse(Transaction transaction) {
        this.transactionCode = transaction.getTransactionCode();
        this.responseCode = transaction.getResponseCode();
        this.createTime = transaction.getCreateTime();
        this.merchantCode = transaction.getMerchantCode();
        this.totalAmount = transaction.getTotalAmount().doubleValue();
        this.accountCode = transaction.getAccountCode();
        this.mmc = transaction.getMmc();
    }
}
