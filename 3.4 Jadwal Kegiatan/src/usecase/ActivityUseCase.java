package usecase;

import domain.entity.Activity;
import domain.repository.IActivityRepository;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ActivityUseCase {
    private static final List<String> DAYS =
            Arrays.asList("senin", "selasa", "rabu", "kamis", "jumat", "sabtu", "minggu");

    private final IActivityRepository repository;

    public ActivityUseCase(IActivityRepository repository) {
        this.repository = repository;
    }

    public Activity addActivity(String title, String day, String time) {
        return repository.save(title.trim(), day.trim(), time.trim());
    }

    public List<Activity> getAll() {
        return repository.findAll();
    }

    /**
     * Mengubah kegiatan. Field yang kosong tidak diubah.
     * @return false jika kegiatan dengan ID tersebut tidak ditemukan
     */
    public boolean updateActivity(int id, String title, String day, String time) {
        Activity a = repository.findById(id);
        if (a == null) return false;
        if (title != null && !title.trim().isEmpty()) a.setTitle(title.trim());
        if (day != null && !day.trim().isEmpty()) a.setDay(day.trim());
        if (time != null && !time.trim().isEmpty()) a.setTime(time.trim());
        return true;
    }

    /** Pencarian berdasarkan judul, tidak peka huruf besar/kecil. */
    public List<Activity> search(String keyword) {
        String key = keyword.toLowerCase();
        return repository.findAll().stream()
                .filter(a -> a.getTitle().toLowerCase().contains(key))
                .collect(Collectors.toList());
    }

    public List<Activity> sort(SortMode mode) {
        List<Activity> list = repository.findAll(); // salinan, data asli tidak berubah
        Comparator<Activity> byTitle =
                Comparator.comparing(Activity::getTitle, String.CASE_INSENSITIVE_ORDER);
        Comparator<Activity> byTime =
                Comparator.comparingInt((Activity a) -> timeKey(a.getTime()));
        Comparator<Activity> byDay =
                Comparator.comparingInt((Activity a) -> dayIndex(a.getDay()))
                        .thenComparingInt(a -> timeKey(a.getTime()));
        Comparator<Activity> cmp;
        switch (mode) {
            case DAY: cmp = byDay; break;
            case TIME: cmp = byTime; break;
            case TITLE_ASC: cmp = byTitle; break;
            default: cmp = byTitle.reversed(); break;
        }
        list.sort(cmp); // List.sort stabil
        return list;
    }

    public boolean delete(int id) {
        return repository.deleteById(id);
    }

    /** Senin = 0 ... Minggu = 6; hari tak dikenal diletakkan paling akhir. */
    private static int dayIndex(String day) {
        int idx = DAYS.indexOf(day.trim().toLowerCase());
        return idx < 0 ? DAYS.size() : idx;
    }

    /** "HH:mm" menjadi menit sejak tengah malam; format lain diletakkan paling akhir. */
    private static int timeKey(String time) {
        String t = time.trim();
        if (t.matches("\\d{1,2}:\\d{2}")) {
            String[] parts = t.split(":");
            return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
        }
        return Integer.MAX_VALUE;
    }
}
