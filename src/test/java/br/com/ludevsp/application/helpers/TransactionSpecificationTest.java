package br.com.ludevsp.application.helpers;

import br.com.ludevsp.api.dto.TransactionQuery;
import br.com.ludevsp.domain.entities.Transaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TransactionSpecificationTest {

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<?> criteriaQuery;

    @Mock
    private Root<Transaction> root;

    @Mock
    private Predicate predicate;

    private TransactionSpecification transactionSpecification;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TransactionQuery transactionQuery = new TransactionQuery();
        transactionQuery.setTransactionCode(1L);
        transactionSpecification = new TransactionSpecification(transactionQuery);
    }

    @Test
    public void testToPredicate() {
        when(criteriaBuilder.equal(any(), any())).thenReturn(predicate);

        transactionSpecification.toPredicate(root, criteriaQuery, criteriaBuilder);

        verify(criteriaBuilder).equal(root.get("transactionCode"), 1L);
    }
}