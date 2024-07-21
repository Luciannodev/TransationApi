package br.com.ludevsp.domain.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountCategoryBalanceKey implements Serializable {
    private long accountCode;
    private int categoryCode;
}
