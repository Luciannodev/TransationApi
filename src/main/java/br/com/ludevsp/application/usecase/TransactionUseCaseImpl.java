package br.com.ludevsp.application.usecase;

import br.com.ludevsp.api.dto.TransactionQuery;
import br.com.ludevsp.application.helpers.TransactionSpecification;
import br.com.ludevsp.domain.entities.AccountCategoryBalance;
import br.com.ludevsp.domain.entities.Category;
import br.com.ludevsp.domain.entities.Transaction;
import br.com.ludevsp.domain.enums.ResponseCode;
import br.com.ludevsp.domain.intefaces.repository.AccountCategoryBalanceRepository;
import br.com.ludevsp.domain.intefaces.repository.CategoryRepository;
import br.com.ludevsp.domain.intefaces.repository.MerchantRepository;
import br.com.ludevsp.domain.intefaces.repository.TransactionRepository;
import br.com.ludevsp.domain.intefaces.usecase.TransactionUseCase;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.ludevsp.domain.enums.CategoryEnum.getCategoryByMcc;

@Service
public class TransactionUseCaseImpl implements TransactionUseCase {
    private final TransactionRepository transactionRepository;
    private final AccountCategoryBalanceRepository accountCategoryBalanceRepository;
    private final MerchantRepository merchantRepository;
    private final CategoryRepository categoryRepository;

    public TransactionUseCaseImpl(TransactionRepository transactionRepository, AccountCategoryBalanceRepository accountCategoryBalanceRepository, MerchantRepository merchantRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.accountCategoryBalanceRepository = accountCategoryBalanceRepository;
        this.merchantRepository = merchantRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Transaction> getAllTransactions(TransactionQuery transactionQuery) {
        Specification<Transaction> spec = new TransactionSpecification(transactionQuery);
        List<Transaction> transactions = transactionRepository.findAll(spec);
        return transactions;
    }

    @Override
    public Transaction execute(Transaction transaction) {
        return TransactionValidation(transaction);
    }

    private Transaction TransactionValidation(Transaction transaction) {
        try {
            attachMerchantDetails(transaction);
            var balanceAccounts = getAccountsBalanceCatetory(transaction);
            var principalAccount = balanceAccounts.get(0);
            var fallbackAccount = balanceAccounts.get(1);
            transaction.setAccount(principalAccount.getAccount());

            if (!processTransaction(principalAccount, transaction) && !processTransaction(fallbackAccount, transaction)) {
                transaction.setResponseMessage("Saldo insuficiente");
                saveTransaction(transaction, ResponseCode.REJEITADA);
            }
            return transaction;
        } catch (Exception e) {
            transaction.setResponseMessage("Erro inesperado");
            saveTransaction(transaction, ResponseCode.ERROINESPERADO);
            transaction.setResponseCode(ResponseCode.ERROINESPERADO);
            return transaction;
        }
    }

    private boolean processTransaction(AccountCategoryBalance account, Transaction transaction) {
        if (hasBalance(account, transaction)) {
            deductBalance(transaction, account);
            transaction.setResponseMessage("Transação aprovada");
            saveTransaction(transaction, ResponseCode.APROVADA);
            return true;
        }
        return false;
    }

    private void attachMerchantDetails(Transaction transaction) {
        var merchantName = transaction.getMerchant().getName().split("               ")[0].toUpperCase();
        var merchant = merchantRepository.findByName(merchantName);
        if (merchant == null) {
            transaction.setMerchant(merchant);
            throw  new RuntimeException("Merchant not found");
        }
        else if (merchant.getMmc() != null) {
            transaction.setMmc(merchant.getMmc());
        }
        transaction.setMerchant(merchant);
    }

    private void deductBalance(Transaction transaction, AccountCategoryBalance principalAccount) {
        principalAccount.setBalance(principalAccount.getBalance().subtract(transaction.getTotalAmount()));
        accountCategoryBalanceRepository.save(principalAccount);
    }

    private Boolean hasBalance(AccountCategoryBalance account, Transaction transaction) {
        return account.getBalance().compareTo(transaction.getTotalAmount()) >= 0;
    }

    private Transaction saveTransaction(Transaction transactionRequest, String responseCode) {

        buildTransactionRequest(transactionRequest, responseCode);
        return transactionRepository.save(transactionRequest);
    }

    private void buildTransactionRequest(Transaction transactionRequest, String responseCode) {
        var Category = categoryRepository.findByCategoryCode(transactionRequest.getMmc());


        transactionRequest.setResponseCode(responseCode);
        transactionRequest.setCategory(Category);
    }

    private List<AccountCategoryBalance> getAccountsBalanceCatetory(Transaction transaction) {
        var categoryName = getCategoryByMcc(transaction.getMmc()).getName();
        if (categoryName == null) {
            throw new RuntimeException("Category not found");
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
