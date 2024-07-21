package br.com.ludevsp.application.usecase;

import br.com.ludevsp.domain.dto.ResponseCode;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class TransitionUseCaseTest {

    @InjectMocks
    private TransactionUseCaseImpl transitionUseCase;

    @Mock
    private TransactionRepository transitionRepository;

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
        when(transitionRepository.save(any())).thenReturn(testHelper.createTransaction());
        when(merchantRepository.findByName(anyString())).thenReturn(new Merchant("Teste", 5411));
        when(accountCategoryBalanceRepository.findByAccountCodeAndCategoryCode(anyLong(), anyInt())).thenReturn(new AccountCategoryBalance());
        when(categoryRepository.findByCategoryCode(anyInt())).thenReturn(new Category(5411, "food"));
        when(categoryRepository.findByName(anyString())).thenReturn(testHelper.createCategories());
        when(accountCategoryBalanceRepository.findByAccountCodeAndCategoryCodeIn(anyLong(), anyList())).thenReturn(testHelper.createAccountCategoryBalances());
    }

    @Test
    public void testExecute() {
        Transaction result = transitionUseCase.execute(testHelper.createTransaction());
        assertEquals(ResponseCode.APROVADA, result.getResponseCode());
    }
}