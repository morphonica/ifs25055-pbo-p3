package usecase;

import domain.entity.Contact;
import domain.repository.IContactRepository;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ContactUseCase {
    private final IContactRepository repository;

    public ContactUseCase(IContactRepository repository) {
        this.repository = repository;
    }

    public Contact addContact(String name, String phone, String email) {
        return repository.save(name.trim(), phone.trim(), email.trim());
    }

    public List<Contact> getAll() {
        return repository.findAll();
    }

    /**
     * Mengubah kontak. Field yang kosong tidak diubah.
     * @return false jika kontak dengan ID tersebut tidak ditemukan
     */
    public boolean updateContact(int id, String name, String phone, String email) {
        Contact c = repository.findById(id);
        if (c == null) return false;
        if (name != null && !name.trim().isEmpty()) c.setName(name.trim());
        if (phone != null && !phone.trim().isEmpty()) c.setPhone(phone.trim());
        if (email != null && !email.trim().isEmpty()) c.setEmail(email.trim());
        return true;
    }

    /** Pencarian berdasarkan nama, tidak peka huruf besar/kecil. */
    public List<Contact> search(String keyword) {
        String key = keyword.toLowerCase();
        return repository.findAll().stream()
                .filter(c -> c.getName().toLowerCase().contains(key))
                .collect(Collectors.toList());
    }

    public List<Contact> sort(SortMode mode) {
        List<Contact> list = repository.findAll(); // salinan, data asli tidak berubah
        Comparator<Contact> byName =
                Comparator.comparing(Contact::getName, String.CASE_INSENSITIVE_ORDER);
        list.sort(mode == SortMode.NAME_ASC ? byName : byName.reversed());
        return list;
    }

    public boolean delete(int id) {
        return repository.deleteById(id);
    }
}
