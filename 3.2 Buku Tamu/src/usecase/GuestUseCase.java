package usecase;

import domain.entity.Guest;
import domain.repository.IGuestRepository;
import java.util.List;
import java.util.stream.Collectors;

public class GuestUseCase {
    private final IGuestRepository repository;

    public GuestUseCase(IGuestRepository repository) {
        this.repository = repository;
    }

    public Guest register(String name, String purpose) {
        return repository.save(name.trim(), purpose.trim());
    }

    public List<Guest> getAll() {
        return repository.findAll();
    }

    /** Pencarian berdasarkan nama, tidak peka huruf besar/kecil. */
    public List<Guest> search(String keyword) {
        String key = keyword.toLowerCase();
        return repository.findAll().stream()
                .filter(g -> g.getName().toLowerCase().contains(key))
                .collect(Collectors.toList());
    }

    public boolean delete(int id) {
        return repository.deleteById(id);
    }
}
