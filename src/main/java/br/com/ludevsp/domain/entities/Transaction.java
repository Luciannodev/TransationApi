package br.com.ludevsp.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_code")
    private long transactionCode;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "response_code")
    private String responseCode;

    @Column(name = "create_time")
    private Date createTime = new Date(System.currentTimeMillis());

    @Column(name = "merchant_code", insertable = false, updatable = false)
    private long merchantCode;

    @ManyToOne
    @JoinColumn(name = "merchant_code")
    private Merchant merchant;

    @Column(name = "account_code", insertable = false, updatable = false)
    private long accountCode;

    @ManyToOne
    @JoinColumn(name = "account_code")
    private Account account;

    @Column(name = "mmc", insertable = false, updatable = false)
    private int mmc;

    @ManyToOne
    @JoinColumn(name = "mmc")
    private Category Category;

    public Transaction(String accountCode, BigDecimal totalAmount, String mmc, String merchantName) {
        this.accountCode = Long.parseLong(accountCode);
        this.totalAmount = totalAmount;
        this.mmc = (mmc != null && !mmc.isEmpty()) ? Integer.parseInt(mmc) : 0;
        this.merchant = new Merchant(merchantName);
    }


}