package domain.repository;

import domain.entity.Contact;
import java.util.List;

public interface IContactRepository {
    Contact save(String name, String phone, String email);
    List<Contact> findAll();
    /** @return kontak dengan ID tersebut, atau null jika tidak ada */
    Contact findById(int id);
    boolean deleteById(int id);
}
