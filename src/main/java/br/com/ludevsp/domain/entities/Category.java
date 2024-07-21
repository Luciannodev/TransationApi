package br.com.ludevsp.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;


@Data
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "name")
    private String name;

    public Category(Integer categoryCode, String name) {
        this.categoryCode = categoryCode;
        this.name = name;
    }
}
