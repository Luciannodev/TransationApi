package br.com.ludevsp.application;

import br.com.ludevsp.domain.dto.ResponseCode;
import br.com.ludevsp.domain.entities.AccountCategoryBalance;
import br.com.ludevsp.domain.entities.Category;
import br.com.ludevsp.domain.entities.Transation;
import br.com.ludevsp.domain.intefaces.repository.AccountCategoryBalanceRepository;
import br.com.ludevsp.domain.intefaces.repository.CategoryRepository;
import br.com.ludevsp.domain.intefaces.repository.MerchantRepository;
import br.com.ludevsp.domain.intefaces.repository.TransationRepository;
import br.com.ludevsp.domain.intefaces.usecase.TransitionUseCase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.ludevsp.domain.enums.CategoryEnum.getCategoryByMcc;

@Service
public class TransitionUseCaseImpl implements TransitionUseCase {
    private TransationRepository transitionRepository;
    private AccountCategoryBalanceRepository accountCategoryBalanceRepository;
    private static MerchantRepository merchantRepository;
    private static CategoryRepository categoryRepository;

    public TransitionUseCaseImpl(TransationRepository transitionRepository, AccountCategoryBalanceRepository accountCategoryBalanceRepository, MerchantRepository merchantRepository, CategoryRepository categoryRepository) {
        this.transitionRepository = transitionRepository;
        this.accountCategoryBalanceRepository = accountCategoryBalanceRepository;
        this.merchantRepository = merchantRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Transation execute(Transation transition) {


        return transationValidation(transition);
    }

    private Transation transationValidation(Transation transition) {
        try {
            fetchAndSetMerchantDetails(transition);
            var balanceAccounts = getAccountCategoryBalance(transition);
            var principalAccount = balanceAccounts.get(0);
            var fallbackAccount = balanceAccounts.get(1);
            transition.setAccount(principalAccount.getAccount());

            if (!processTransaction(principalAccount, transition) && !processTransaction(fallbackAccount, transition)) {
                saveTransition(transition, ResponseCode.REJEITADA);
            }
            return transition;
        } catch (Exception e) {
            transition.setResponseCode(ResponseCode.ERROINESPERADO);
            return transition;
        }
    }

    private boolean processTransaction(AccountCategoryBalance account, Transation transition) {
        if (haveBalance(account, transition)) {
            deductBalance(transition, account);
            saveTransition(transition, ResponseCode.APROVADA);
            return true;
        }
        return false;
    }

    private static void fetchAndSetMerchantDetails(Transation transition) {
        var merchant = merchantRepository.findByName(transition.getMerchant().getName());
        if (merchant.getMmc() != 0) {
            transition.setMmc(merchant.getMmc());
        }
        transition.setMerchant(merchant);
    }

    private void deductBalance(Transation transition, AccountCategoryBalance principalAccount) {
        principalAccount.setBalance(principalAccount.getBalance().subtract(transition.getTotalAmount()));
        accountCategoryBalanceRepository.save(principalAccount);
    }

    private Boolean haveBalance(AccountCategoryBalance account, Transation transition) {
        return account.getBalance().compareTo(transition.getTotalAmount()) >= 0;
    }

    private Transation saveTransition(Transation transitionRequest, String responseCode) {

        buildTransationRequest(transitionRequest, responseCode);
        return transitionRepository.save(transitionRequest);
    }

    private static void buildTransationRequest(Transation transitionRequest, String responseCode) {
        var Category = categoryRepository.findByCategoryCode(transitionRequest.getMmc());


        transitionRequest.setResponseCode(responseCode);
        transitionRequest.setCategory(Category);
    }

    private List<AccountCategoryBalance> getAccountCategoryBalance(Transation transition) {
        var categoryName = getCategoryByMcc(transition.getMmc()).getName();
        if(categoryName == null) {
            categoryName = "Other";
        }
        List<Integer> categoryCodes = categoryRepository.findByName(categoryName).stream().map(Category::getCategoryCode).collect(Collectors.toList());
        categoryCodes.add(transition.getMmc());
        categoryCodes.add(9999);
        var accounts = accountCategoryBalanceRepository.findByAccountCodeAndCategoryCodeIn(transition.getAccountCode(), categoryCodes);
        if (accounts.isEmpty()) {
            throw new RuntimeException("Account not found");
        }
        return accounts;
    }
}
