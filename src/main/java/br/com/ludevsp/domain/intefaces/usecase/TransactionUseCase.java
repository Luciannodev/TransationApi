package br.com.ludevsp.domain.intefaces.usecase;


import br.com.ludevsp.domain.entities.Transaction;

public interface TransactionUseCase {
   public Transaction execute(Transaction transaction);
}
