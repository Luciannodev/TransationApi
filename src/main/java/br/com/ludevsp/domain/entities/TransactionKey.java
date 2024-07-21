package br.com.ludevsp.domain.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class TransactionKey implements Serializable {
    private long transactionCode;
}
