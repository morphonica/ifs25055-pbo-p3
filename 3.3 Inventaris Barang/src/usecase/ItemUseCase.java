package usecase;

import domain.entity.Item;
import domain.repository.IItemRepository;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ItemUseCase {
    private final IItemRepository repository;

    public ItemUseCase(IItemRepository repository) {
        this.repository = repository;
    }

    /** @throws IllegalArgumentException jika stok negatif */
    public Item addItem(String name, long stock, String category) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stok tidak boleh negatif");
        }
        return repository.save(name.trim(), stock, category.trim());
    }

    public List<Item> getAll() {
        return repository.findAll();
    }

    /**
     * Mengubah stok barang.
     * @param newStock stok baru, atau null jika tidak ingin mengubah
     * @return false jika barang dengan ID tersebut tidak ditemukan
     */
    public boolean updateStock(int id, Long newStock) {
        Item item = repository.findById(id);
        if (item == null) return false;
        if (newStock != null) {
            if (newStock < 0) {
                throw new IllegalArgumentException("Stok tidak boleh negatif");
            }
            item.setStock(newStock);
        }
        return true;
    }

    public List<Item> search(String keyword) {
        String key = keyword.toLowerCase();
        return repository.findAll().stream()
                .filter(i -> i.getName().toLowerCase().contains(key))
                .collect(Collectors.toList());
    }

    public List<Item> sort(SortMode mode) {
        List<Item> list = repository.findAll(); // salinan, data asli tidak berubah
        Comparator<Item> byName = Comparator.comparing(Item::getName, String.CASE_INSENSITIVE_ORDER);
        Comparator<Item> byStock = Comparator.comparingLong(Item::getStock);
        Comparator<Item> cmp;
        switch (mode) {
            case NAME_ASC: cmp = byName; break;
            case NAME_DESC: cmp = byName.reversed(); break;
            case STOCK_ASC: cmp = byStock; break;
            default: cmp = byStock.reversed(); break;
        }
        list.sort(cmp); // List.sort stabil
        return list;
    }

    public boolean delete(int id) {
        return repository.deleteById(id);
    }
}
