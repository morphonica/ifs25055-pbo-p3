package adapter.repository;

import domain.entity.Item;
import domain.repository.IItemRepository;
import java.util.ArrayList;
import java.util.List;

public class ItemRepository implements IItemRepository {
    private final List<Item> data = new ArrayList<>();
    private int nextId = 1; // ID tidak dipakai ulang setelah penghapusan

    @Override
    public Item save(String name, long stock, String category) {
        Item item = new Item(nextId++, name, stock, category);
        data.add(item);
        return item;
    }

    @Override
    public List<Item> findAll() {
        return new ArrayList<>(data);
    }

    @Override
    public Item findById(int id) {
        for (Item item : data) {
            if (item.getId() == id) return item;
        }
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(i -> i.getId() == id);
    }
}
