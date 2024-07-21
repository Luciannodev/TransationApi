package br.com.ludevsp.domain.intefaces.repository;

import br.com.ludevsp.domain.entities.AccountCategoryBalance;
import br.com.ludevsp.domain.entities.AccountCategoryBalanceKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountCategoryBalanceRepository extends JpaRepository<AccountCategoryBalance, AccountCategoryBalanceKey>{
    AccountCategoryBalance findByAccountCodeAndCategoryCode(Long accountCode, Integer categoryCode);
    List<AccountCategoryBalance> findByAccountCodeAndCategoryCodeIn(Long accountCode, List<Integer> categoryCodes);

}
