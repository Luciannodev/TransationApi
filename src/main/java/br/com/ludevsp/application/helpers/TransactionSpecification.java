package br.com.ludevsp.application.helpers;

import br.com.ludevsp.api.dto.TransactionQuery;
import br.com.ludevsp.domain.entities.Transaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class TransactionSpecification implements Specification<Transaction> {

    private TransactionQuery transactionQuery;

    public TransactionSpecification(TransactionQuery transactionQuery) {
        this.transactionQuery = transactionQuery;
    }

    @Override
    public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        for (Field field : TransactionQuery.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(transactionQuery);
                if (value != null) {
                    predicates.add(criteriaBuilder.equal(root.get(field.getName()), value));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}