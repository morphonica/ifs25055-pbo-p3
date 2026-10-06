package adapter.repository;

import domain.entity.Contact;
import domain.repository.IContactRepository;
import java.util.ArrayList;
import java.util.List;

public class ContactRepository implements IContactRepository {
    private final List<Contact> data = new ArrayList<>();
    private int nextId = 1; // ID tidak dipakai ulang setelah penghapusan

    @Override
    public Contact save(String name, String phone, String email) {
        Contact c = new Contact(nextId++, name, phone, email);
        data.add(c);
        return c;
    }

    @Override
    public List<Contact> findAll() {
        return new ArrayList<>(data);
    }

    @Override
    public Contact findById(int id) {
        for (Contact c : data) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(c -> c.getId() == id);
    }
}
