package br.com.ludevsp.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
@Table(name = "transation")
public class Transation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transation_code")
    private long transationCode;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "response_code")
    private String responseCode;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "merchant_code", insertable = false, updatable = false)
    private long merchantCode;

    @ManyToOne
    @JoinColumn(name = "merchant_code", nullable = false)
    private Merchant merchant;

    @Column(name = "account_code", insertable = false, updatable = false)
    private long accountCode;

    @ManyToOne
    @JoinColumn(name = "account_code", nullable = false)
    private Account account;

    @Column(name = "mmc", insertable = false, updatable = false)
    private int mmc;

    @ManyToOne
    @JoinColumn(name = "mmc", nullable = false)
    private Category Category;

    public Transation(String accountCode, BigDecimal totalAmount, String mmc, String merchantName) {
        this.accountCode = Long.parseLong(accountCode);
        this.totalAmount = totalAmount;
        this.mmc = (mmc != null && !mmc.isEmpty()) ? Integer.parseInt(mmc) : 0;
        this.merchant = new Merchant(merchantName);
    }
}