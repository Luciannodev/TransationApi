package br.com.ludevsp.domain.intefaces.usecase;


import br.com.ludevsp.api.dto.TransactionQuery;
import br.com.ludevsp.api.dto.TransactionResponse;
import br.com.ludevsp.domain.entities.Transaction;

import java.util.List;

public interface TransactionUseCase {
   public Transaction execute(Transaction transaction);

   List<Transaction> getAllTransactions(TransactionQuery transactionQuery);
}
