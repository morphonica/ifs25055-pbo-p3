package domain.repository;

import domain.entity.Transaction;
import domain.entity.TransactionType;
import java.util.List;

public interface ITransactionRepository {
    Transaction save(String description, long amount, TransactionType type);
    List<Transaction> findAll();
    boolean deleteById(int id);
}
