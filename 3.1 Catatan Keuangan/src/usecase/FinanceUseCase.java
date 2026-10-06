package usecase;

import domain.entity.Transaction;
import domain.entity.TransactionType;
import domain.repository.ITransactionRepository;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FinanceUseCase {
    private final ITransactionRepository repository;

    public FinanceUseCase(ITransactionRepository repository) {
        this.repository = repository;
    }

    /** @throws IllegalArgumentException jika jumlah tidak positif */
    public Transaction addTransaction(String description, long amount, TransactionType type) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Jumlah harus positif");
        }
        return repository.save(description.trim(), amount, type);
    }

    public List<Transaction> getAll() {
        return repository.findAll();
    }

    public long getBalance() {
        long balance = 0;
        for (Transaction t : repository.findAll()) {
            balance += t.isIncome() ? t.getAmount() : -t.getAmount();
        }
        return balance;
    }

    public List<Transaction> search(String keyword) {
        String key = keyword.toLowerCase();
        return repository.findAll().stream()
                .filter(t -> t.getDescription().toLowerCase().contains(key))
                .collect(Collectors.toList());
    }

    public List<Transaction> sort(SortMode mode) {
        List<Transaction> list = repository.findAll(); // salinan, data asli tidak berubah
        Comparator<Transaction> cmp;
        switch (mode) {
            case AMOUNT_ASC:
                cmp = Comparator.comparingLong(Transaction::getAmount);
                break;
            case AMOUNT_DESC:
                cmp = Comparator.comparingLong(Transaction::getAmount).reversed();
                break;
            case INCOME_FIRST:
                cmp = Comparator.comparingInt(t -> t.getType() == TransactionType.INCOME ? 0 : 1);
                break;
            default:
                cmp = Comparator.comparingInt(t -> t.getType() == TransactionType.EXPENSE ? 0 : 1);
                break;
        }
        list.sort(cmp); // List.sort stabil
        return list;
    }

    public boolean delete(int id) {
        return repository.deleteById(id);
    }
}
