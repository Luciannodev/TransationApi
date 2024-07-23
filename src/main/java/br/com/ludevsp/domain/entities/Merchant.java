package br.com.ludevsp.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
@Table(name = "merchant")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "merchant_code")
    private long merchantCode;

    @Column(name = "name")
    private String name;

    @Column(name = "create_time")
    private Date createTime = new Date(System.currentTimeMillis());

    @Column(name = "mmc", insertable = false, updatable = false)
    private Integer mmc;

    @ManyToOne
    @JoinColumn(name = "mmc")
    private Category Category;

    public Merchant(String name) {
        this.name = name;
    }

    public Merchant(String name, int mmc) {
        this.name = name;
        this.mmc = mmc;
    }
}