package br.com.ludevsp.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TransactionQuery {
    @JsonProperty("transactionCode")
    private Long transactionCode;

    @JsonProperty("create_time")
    private Timestamp createTime;

    @JsonProperty("merchantCode")
    private Long merchantCode;

    @JsonProperty("accountCode")
    private Long accountCode;

    @JsonProperty("mmc")
    private Integer mmc;
}