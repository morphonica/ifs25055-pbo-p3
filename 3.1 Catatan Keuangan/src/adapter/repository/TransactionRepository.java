package adapter.repository;

import domain.entity.Transaction;
import domain.entity.TransactionType;
import domain.repository.ITransactionRepository;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository implements ITransactionRepository {
    private final List<Transaction> data = new ArrayList<>();
    private int nextId = 1; // tidak dipakai ulang setelah penghapusan

    @Override
    public Transaction save(String description, long amount, TransactionType type) {
        Transaction t = new Transaction(nextId++, description, amount, type);
        data.add(t);
        return t;
    }

    @Override
    public List<Transaction> findAll() {
        return new ArrayList<>(data);
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(t -> t.getId() == id);
    }
}
