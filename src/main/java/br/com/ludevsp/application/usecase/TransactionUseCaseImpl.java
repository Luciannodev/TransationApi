package br.com.ludevsp.application.usecase;

import br.com.ludevsp.domain.dto.ResponseCode;
import br.com.ludevsp.domain.entities.AccountCategoryBalance;
import br.com.ludevsp.domain.entities.Category;
import br.com.ludevsp.domain.entities.Transaction;
import br.com.ludevsp.domain.intefaces.repository.AccountCategoryBalanceRepository;
import br.com.ludevsp.domain.intefaces.repository.CategoryRepository;
import br.com.ludevsp.domain.intefaces.repository.MerchantRepository;
import br.com.ludevsp.domain.intefaces.repository.TransactionRepository;
import br.com.ludevsp.domain.intefaces.usecase.TransactionUseCase;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import static br.com.ludevsp.domain.enums.CategoryEnum.getCategoryByMcc;

@Service
public class TransactionUseCaseImpl implements TransactionUseCase {
    private TransactionRepository transactionRepository;
    private AccountCategoryBalanceRepository accountCategoryBalanceRepository;
    private static MerchantRepository merchantRepository;
    private static CategoryRepository categoryRepository;

    public TransactionUseCaseImpl(TransactionRepository transactionRepository, AccountCategoryBalanceRepository accountCategoryBalanceRepository, MerchantRepository merchantRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.accountCategoryBalanceRepository = accountCategoryBalanceRepository;
        this.merchantRepository = merchantRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Transaction execute(Transaction transaction) {


        return TransactionValidation(transaction);
    }

    private Transaction TransactionValidation(Transaction transaction) {
        try {
            fetchAndSetMerchantDetails(transaction);
            var balanceAccounts = getAccountCategoryBalance(transaction);
            var principalAccount = balanceAccounts.get(0);
            var fallbackAccount = balanceAccounts.get(1);
            transaction.setAccount(principalAccount.getAccount());

            if (!processTransaction(principalAccount, transaction) && !processTransaction(fallbackAccount, transaction)) {
                savetransaction(transaction, ResponseCode.REJEITADA);
            }
            return transaction;
        } catch (Exception e) {
            transaction.setResponseCode(ResponseCode.ERROINESPERADO);
            return transaction;
        }
    }

    private boolean processTransaction(AccountCategoryBalance account, Transaction transaction) {
        if (haveBalance(account, transaction)) {
            deductBalance(transaction, account);
            savetransaction(transaction, ResponseCode.APROVADA);
            return true;
        }
        return false;
    }

    private static void fetchAndSetMerchantDetails(Transaction transaction) {
        var merchant = merchantRepository.findByName(transaction.getMerchant().getName());
        if (merchant.getMmc() != 0) {
            transaction.setMmc(merchant.getMmc());
        }
        transaction.setMerchant(merchant);
    }

    private void deductBalance(Transaction transaction, AccountCategoryBalance principalAccount) {
        principalAccount.setBalance(principalAccount.getBalance().subtract(transaction.getTotalAmount()));
        accountCategoryBalanceRepository.save(principalAccount);
    }

    private Boolean haveBalance(AccountCategoryBalance account, Transaction transaction) {
        return account.getBalance().compareTo(transaction.getTotalAmount()) >= 0;
    }

    private Transaction savetransaction(Transaction transactionRequest, String responseCode) {

        buildTransactionRequest(transactionRequest, responseCode);
        return transactionRepository.save(transactionRequest);
    }

    private static void buildTransactionRequest(Transaction transactionRequest, String responseCode) {
        var Category = categoryRepository.findByCategoryCode(transactionRequest.getMmc());


        transactionRequest.setResponseCode(responseCode);
        transactionRequest.setCategory(Category);
    }

    private List<AccountCategoryBalance> getAccountCategoryBalance(Transaction transaction) {
        var categoryName = getCategoryByMcc(transaction.getMmc()).getName();
        if(categoryName == null) {
            categoryName = "Other";
        }
        List<Integer> categoryCodes = categoryRepository.findByName(categoryName).stream().map(Category::getCategoryCode).collect(Collectors.toList());
        categoryCodes.add(transaction.getMmc());
        categoryCodes.add(9999);
        var accounts = accountCategoryBalanceRepository.findByAccountCodeAndCategoryCodeIn(transaction.getAccountCode(), categoryCodes);
        if (accounts.isEmpty()) {
            throw new RuntimeException("Account not found");
        }
        return accounts;
    }
}
