package helpers;

import br.com.ludevsp.domain.enums.ResponseCode;
import br.com.ludevsp.domain.entities.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestHelper {

    public Transaction createTransaction() {
        Transaction transaction = new Transaction();
        transaction.setResponseCode(ResponseCode.APROVADA);
        transaction.setMmc(5411);
        transaction.setTotalAmount(new BigDecimal(100));
        transaction.setAccountCode(123);
        transaction.setMerchant(new Merchant("Teste"));
        return transaction;
    }

    public Transaction createTransactionWhithoutAcount() {
        Transaction transaction = new Transaction();
        transaction.setResponseCode(ResponseCode.APROVADA);
        transaction.setMmc(5411);
        transaction.setTotalAmount(new BigDecimal(100));
        transaction.setMerchant(new Merchant("Teste"));
        return transaction;
    }

    public ArrayList<Category> createCategories() {
        return new ArrayList<>() {
            {
                add(new Category(5411, "food"));
            }
        };
    }

    public List<AccountCategoryBalance> createAccountCategoryBalances() {
        return new ArrayList<>() {
            {
                add(new AccountCategoryBalance(123, 5411, new BigDecimal(100), new Account(), new Category()));
                add(new AccountCategoryBalance(123, 9999, new BigDecimal(100), new Account(), new Category()));
            }
        };
    }
}