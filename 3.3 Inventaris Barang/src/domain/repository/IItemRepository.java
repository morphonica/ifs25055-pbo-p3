package domain.repository;

import domain.entity.Item;
import java.util.List;

public interface IItemRepository {
    Item save(String name, long stock, String category);
    List<Item> findAll();
    /** @return item dengan ID tersebut, atau null jika tidak ada */
    Item findById(int id);
    boolean deleteById(int id);
}
