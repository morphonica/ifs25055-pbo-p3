package adapter.repository;

import domain.entity.Activity;
import domain.repository.IActivityRepository;
import java.util.ArrayList;
import java.util.List;

public class ActivityRepository implements IActivityRepository {
    private final List<Activity> data = new ArrayList<>();
    private int nextId = 1; // ID tidak dipakai ulang setelah penghapusan

    @Override
    public Activity save(String title, String day, String time) {
        Activity a = new Activity(nextId++, title, day, time);
        data.add(a);
        return a;
    }

    @Override
    public List<Activity> findAll() {
        return new ArrayList<>(data);
    }

    @Override
    public Activity findById(int id) {
        for (Activity a : data) {
            if (a.getId() == id) return a;
        }
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(a -> a.getId() == id);
    }
}
