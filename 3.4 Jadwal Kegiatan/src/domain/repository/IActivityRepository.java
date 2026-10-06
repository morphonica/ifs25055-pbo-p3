package domain.repository;

import domain.entity.Activity;
import java.util.List;

public interface IActivityRepository {
    Activity save(String title, String day, String time);
    List<Activity> findAll();
    /** @return kegiatan dengan ID tersebut, atau null jika tidak ada */
    Activity findById(int id);
    boolean deleteById(int id);
}
