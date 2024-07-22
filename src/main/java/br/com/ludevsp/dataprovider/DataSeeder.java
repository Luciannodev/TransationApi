package br.com.ludevsp.dataprovider;

import br.com.ludevsp.domain.entities.Account;
import br.com.ludevsp.domain.entities.AccountCategoryBalance;
import br.com.ludevsp.domain.entities.Category;
import br.com.ludevsp.domain.entities.Merchant;
import br.com.ludevsp.domain.enums.CategoryEnum;
import br.com.ludevsp.domain.intefaces.repository.AccountCategoryBalanceRepository;
import br.com.ludevsp.domain.intefaces.repository.AccountRepository;
import br.com.ludevsp.domain.intefaces.repository.CategoryRepository;
import br.com.ludevsp.domain.intefaces.repository.MerchantRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class DataSeeder {
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;

    private final MerchantRepository merchantRepository;

    private final AccountCategoryBalanceRepository accountCategoryBalanceRepository;


    public DataSeeder(AccountRepository accountRepository, CategoryRepository categoryRepository, MerchantRepository merchantRepository, AccountCategoryBalanceRepository accountCategoryBalanceRepository) {
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
        this.merchantRepository = merchantRepository;
        this.accountCategoryBalanceRepository = accountCategoryBalanceRepository;
    }

    public void seedDataCategory() {
        if (categoryRepository.count() == CategoryEnum.values().length) {
            return;
        }

        for (CategoryEnum category : CategoryEnum.values()) {
            for(Integer mcc : category.getMccCodes()) {
                if (categoryRepository.findByCategoryCode(mcc) == null) {
                    categoryRepository.save(new Category(mcc, category.getName()));
                }
            }
        }
    }

    public void seedMerchantDataMock() {
        if (merchantRepository.findByName("PADARIA DO ZE               SAO PAULO BR") == null) {
            Merchant merchant = new Merchant();
            merchant.setName("PADARIA DO ZE               SAO PAULO BR");
            merchant.setMmc(5411);

            // Busca a categoria no banco de dados
            Category category = categoryRepository.findById((long) merchant.getMmc()).orElse(null);
            if (category == null) {
                // Se a categoria não existir, cria uma nova
                category = new Category(merchant.getMmc(), "Food");
                categoryRepository.save(category);
            }

            // Define a categoria do comerciante
            merchant.setCategory(category);

            merchantRepository.save(merchant);
        }
    }
    public void seedMockAccount() {
        if(accountRepository.count() > 0) {
            return;
        }

        // Cria uma nova conta
        Account account = new Account();
        accountRepository.save(account);

        // Para cada categoria, cria um AccountCategoryBalance
        for (CategoryEnum categoryEnum : CategoryEnum.values()) {
            Category category = categoryRepository.findById(Long.valueOf(categoryEnum.getMccCodes().get(0))).orElse(null);
            if (category != null) {
                AccountCategoryBalance accountCategoryBalance = new AccountCategoryBalance();
                accountCategoryBalance.setAccount(account);
                accountCategoryBalance.setCategoryBalance(category);
                accountCategoryBalance.setBalance(new BigDecimal("300.00")); // Define um valor de saldo
                accountCategoryBalanceRepository.save(accountCategoryBalance);
            }
        }
    }
}
