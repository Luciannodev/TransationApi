package br.com.ludevsp.api.dto;

import br.com.ludevsp.domain.entities.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TransactionResponse {

    @JsonProperty("transactionCode")
    private long transactionCode;

    @JsonProperty("create_time")
    private Date createTime;

    @JsonProperty("merchantCode")
    private long merchantCode;

    @JsonProperty("totalAmount")
    private BigDecimal totalAmount;

    @JsonProperty("accountCode")
    private long accountCode;

    @JsonProperty("mmc")
    private int mmc;

    @JsonProperty("Message")
    private String responseMessage;

    @JsonProperty("code")
    private String responseCode;

    public TransactionResponse(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public TransactionResponse(Transaction transaction) {
        this.transactionCode = transaction.getTransactionCode();
        this.responseCode = transaction.getResponseCode();
        this.createTime = transaction.getCreateTime();
        this.merchantCode = transaction.getMerchantCode();
        this.totalAmount = transaction.getTotalAmount();
        this.accountCode = transaction.getAccountCode();
        this.mmc = transaction.getMmc();
        this.responseMessage = transaction.getResponseMessage();
    }
}
