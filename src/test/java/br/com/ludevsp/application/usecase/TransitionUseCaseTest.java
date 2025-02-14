package br.com.ludevsp.application.usecase;

import br.com.ludevsp.api.dto.TransactionQuery;
import br.com.ludevsp.domain.enums.ResponseCode;
import br.com.ludevsp.domain.entities.AccountCategoryBalance;
import br.com.ludevsp.domain.entities.Category;
import br.com.ludevsp.domain.entities.Merchant;
import br.com.ludevsp.domain.entities.Transaction;
import br.com.ludevsp.domain.intefaces.repository.AccountCategoryBalanceRepository;
import br.com.ludevsp.domain.intefaces.repository.CategoryRepository;
import br.com.ludevsp.domain.intefaces.repository.MerchantRepository;
import br.com.ludevsp.domain.intefaces.repository.TransactionRepository;
import helpers.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class TransitionUseCaseTest {

    @InjectMocks
    private TransactionUseCaseImpl transactionUseCase;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountCategoryBalanceRepository accountCategoryBalanceRepository;

    @Mock
    private MerchantRepository merchantRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private final TestHelper testHelper = new TestHelper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        setupMocks();
    }

    private void setupMocks() {
        when(merchantRepository.findByName(anyString())).thenReturn(new Merchant("Teste", 5411));
        when(categoryRepository.findByCategoryCode(anyInt())).thenReturn(new Category(5411, "food"));
        when(categoryRepository.findByName(anyString())).thenReturn(testHelper.createCategories());
    }

    @Test
    public void testExecute() {
        when(accountCategoryBalanceRepository.findByAccountCodeAndCategoryCodeIn(anyLong(), anyList())).thenReturn(testHelper.createAccountCategoryBalances());
        when(accountCategoryBalanceRepository.findByAccountCodeAndCategoryCode(anyLong(), anyInt())).thenReturn(new AccountCategoryBalance());
        when(transactionRepository.save(any())).thenReturn(testHelper.createTransaction());
        Transaction result = transactionUseCase.execute(testHelper.createTransaction());
        assertEquals(ResponseCode.APROVADA, result.getResponseCode());
    }

    @Test
    public void testExecuteWithoutBalance() {
        when(accountCategoryBalanceRepository.findByAccountCodeAndCategoryCodeIn(anyLong(), anyList())).thenReturn(testHelper.createAccountCategoryBalances());
        when(accountCategoryBalanceRepository.findByAccountCodeAndCategoryCode(anyLong(), anyInt())).thenReturn(new AccountCategoryBalance());
        when(transactionRepository.save(any())).thenReturn(testHelper.createTransaction());
        Transaction transaction = testHelper.createTransaction();
        transaction.setTotalAmount(new java.math.BigDecimal(1000));
        transactionUseCase.execute(transaction);
        assertEquals(ResponseCode.REJEITADA, transaction.getResponseCode());
    }
    @Test
    public void testExecuteWithoutAccount() {
        Transaction transactionWithoutAccount = testHelper.createTransactionWhithoutAcount();
        transactionWithoutAccount.setAccount(null);
        transactionUseCase.execute(transactionWithoutAccount);
        assertEquals(ResponseCode.ERROINESPERADO, transactionWithoutAccount.getResponseCode());
    }

    @Test
    public void testGetAllTransactions() {
        TransactionQuery transactionQuery = new TransactionQuery();
        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();
        List<Transaction> expectedTransactions = Arrays.asList(transaction1, transaction2);

        when(transactionRepository.findAll(any(Specification.class))).thenReturn(expectedTransactions);

        List<Transaction> actualTransactions = transactionUseCase.getAllTransactions(transactionQuery);

        assertEquals(expectedTransactions, actualTransactions);
    }



}