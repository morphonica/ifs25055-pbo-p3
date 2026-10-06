package framework.view;

import adapter.presenter.ActivityPresenter;
import domain.entity.Activity;
import java.util.List;
import java.util.Scanner;
import usecase.ActivityUseCase;
import usecase.SortMode;

public class ActivityView {
    private final ActivityUseCase useCase;
    private final ActivityPresenter presenter;
    private final Scanner in = new Scanner(System.in);

    public ActivityView(ActivityUseCase useCase, ActivityPresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        boolean first = true;
        while (true) {
            if (!first) System.out.println();
            first = false;

            System.out.print(presenter.formatDashboard(useCase.getAll()));
            String choice = readChoice();
            if (choice == null || choice.equalsIgnoreCase("x")) break;

            switch (choice) {
                case "1": addActivity(); break;
                case "2": updateActivity(); break;
                case "3": search(); break;
                case "4": sort(); break;
                case "5": delete(); break;
                default: System.out.println(presenter.invalidMenu());
            }
        }
    }

    /** Prompt "Pilih : " lalu pindah baris (sesuai format output test case). */
    private String readChoice() {
        System.out.print("Pilih : ");
        String line = readLine();
        System.out.println();
        return line;
    }

    private String readLine() {
        if (!in.hasNextLine()) return null;
        return in.nextLine().trim();
    }

    private boolean isCancel(String s) {
        return s == null || s.equalsIgnoreCase("x");
    }

    private void addActivity() {
        System.out.println("[Menambah Kegiatan]");
        System.out.print("Judul (x Jika Batal) : ");
        String title = readLine();
        if (isCancel(title)) return;

        System.out.print("Hari (x Jika Batal) : ");
        String day = readLine();
        if (isCancel(day)) return;

        System.out.print("Waktu (x Jika Batal) : ");
        String time = readLine();
        if (isCancel(time)) return;

        Activity a = useCase.addActivity(title, day, time);
        System.out.println(presenter.added(a));
    }

    private void updateActivity() {
        System.out.println("[Mengubah Kegiatan]");
        System.out.print("ID Kegiatan yang diubah (x Jika Batal) : ");
        String idStr = readLine();
        if (isCancel(idStr)) return;

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.out.println(presenter.invalidId());
            return;
        }

        System.out.print("Judul Baru (Kosongkan jika tidak ingin mengubah) : ");
        String title = readLine();
        System.out.print("Hari Baru (Kosongkan jika tidak ingin mengubah) : ");
        String day = readLine();
        System.out.print("Waktu Baru (Kosongkan jika tidak ingin mengubah) : ");
        String time = readLine();

        if (useCase.updateActivity(id, title, day, time)) {
            System.out.println(presenter.updated());
        } else {
            System.out.println(presenter.updateFailed(id));
        }
    }

    private void search() {
        System.out.println("[Mencari Kegiatan]");
        System.out.print("Kata Kunci (x Jika Batal) : ");
        String keyword = readLine();
        if (isCancel(keyword)) return;

        System.out.println(presenter.searchHeader(keyword));
        List<Activity> result = useCase.search(keyword);
        if (result.isEmpty()) {
            System.out.println(presenter.notFound());
        } else {
            for (Activity a : result) {
                System.out.println(presenter.formatActivity(a));
            }
        }
    }

    private void sort() {
        System.out.println("[Mengurutkan Kegiatan]");
        System.out.print(presenter.sortMenu());
        String choice = readChoice();
        if (isCancel(choice)) return;

        SortMode mode;
        switch (choice) {
            case "1": mode = SortMode.DAY; break;
            case "2": mode = SortMode.TIME; break;
            case "3": mode = SortMode.TITLE_ASC; break;
            case "4": mode = SortMode.TITLE_DESC; break;
            default:
                System.out.println(presenter.invalidSort());
                return;
        }
        System.out.print(presenter.formatList("Daftar Kegiatan (Terurut):", useCase.sort(mode)));
    }

    private void delete() {
        System.out.println("[Menghapus Kegiatan]");
        System.out.print("[ID Kegiatan] yang dihapus (x Jika Batal) : ");
        String idStr = readLine();
        if (isCancel(idStr)) return;

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.out.println(presenter.invalidId());
            return;
        }
        if (useCase.delete(id)) {
            System.out.println(presenter.deleted());
        } else {
            System.out.println(presenter.deleteFailed(id));
        }
    }
}
