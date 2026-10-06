package adapter.repository;

import domain.entity.Guest;
import domain.repository.IGuestRepository;
import java.util.ArrayList;
import java.util.List;

public class GuestRepository implements IGuestRepository {
    private final List<Guest> data = new ArrayList<>();
    private int nextId = 1; // ID tidak dipakai ulang setelah penghapusan

    @Override
    public Guest save(String name, String purpose) {
        Guest g = new Guest(nextId++, name, purpose);
        data.add(g);
        return g;
    }

    @Override
    public List<Guest> findAll() {
        return new ArrayList<>(data);
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(g -> g.getId() == id);
    }
}
