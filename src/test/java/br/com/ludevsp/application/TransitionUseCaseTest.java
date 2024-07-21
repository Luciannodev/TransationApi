package br.com.ludevsp.application;

import br.com.ludevsp.domain.dto.ResponseCode;
import br.com.ludevsp.domain.entities.Merchant;
import br.com.ludevsp.domain.entities.Transation;
import br.com.ludevsp.domain.intefaces.repository.AccountCategoryBalanceRepository;
import br.com.ludevsp.domain.intefaces.repository.CategoryRepository;
import br.com.ludevsp.domain.intefaces.repository.MerchantRepository;
import br.com.ludevsp.domain.intefaces.repository.TransationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TransitionUseCaseTest {

    @InjectMocks
    private TransitionUseCaseImpl transitionUseCase;

    @Mock
    private TransationRepository transitionRepository;

    @Mock
    private AccountCategoryBalanceRepository accountCategoryBalanceRepository;

    @Mock
    private MerchantRepository merchantRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testExecute() {
        Transation transition = new Transation();
        transition.setMmc(5411);
        transition.setTotalAmount(new BigDecimal(100));
        transition.setAccountCode(123);
        transition.setMerchant(new Merchant("Teste"));

        when(transitionRepository.save(transition)).thenReturn(transition);

        Transation result = transitionUseCase.execute(transition);

        assertEquals(ResponseCode.APROVADA, result.getResponseCode());
    }

}