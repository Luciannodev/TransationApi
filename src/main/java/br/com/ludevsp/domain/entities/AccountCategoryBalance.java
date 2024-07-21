package br.com.ludevsp.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@IdClass(AccountCategoryBalanceKey.class)
@Entity
@Table(name = "account_category_balance")
public class AccountCategoryBalance {

    @Id
    @Column(name = "account_code")
    private long accountCode;

    @Id
    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "balance")
    private BigDecimal balance;

    @MapsId("accountCode")
    @ManyToOne
    @JoinColumn(name = "account_code", nullable = false)
    private Account account;

    @MapsId("categoryCode")
    @ManyToOne
    @JoinColumn(name = "category_code", nullable = false)
    private Category categoryBalance;

    public AccountCategoryBalance(long accountCode, int categoryCode, BigDecimal balance, Account account, Category categoryBalance) {
        this.accountCode = accountCode;
        this.categoryCode = categoryCode;
        this.balance = balance;
        this.account = account;
        this.categoryBalance = categoryBalance;
    }
}