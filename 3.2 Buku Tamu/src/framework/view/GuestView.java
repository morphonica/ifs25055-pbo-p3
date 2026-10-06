package framework.view;

import adapter.presenter.GuestPresenter;
import domain.entity.Guest;
import java.util.List;
import java.util.Scanner;
import usecase.GuestUseCase;

public class GuestView {
    private final GuestUseCase useCase;
    private final GuestPresenter presenter;
    private final Scanner in = new Scanner(System.in);

    public GuestView(GuestUseCase useCase, GuestPresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        boolean first = true;
        while (true) {
            if (!first) System.out.println();
            first = false;

            System.out.print(presenter.formatDashboard(useCase.getAll()));
            System.out.print("Pilih : ");
            String choice = readLine();
            System.out.println();
            if (choice == null || choice.equalsIgnoreCase("x")) break;

            switch (choice) {
                case "1": register(); break;
                case "2": search(); break;
                case "3": delete(); break;
                default: System.out.println(presenter.invalidMenu());
            }
        }
    }

    private String readLine() {
        if (!in.hasNextLine()) return null;
        return in.nextLine().trim();
    }

    private boolean isCancel(String s) {
        return s == null || s.equalsIgnoreCase("x");
    }

    private void register() {
        System.out.println("[Mendaftarkan Tamu]");
        System.out.print("Nama (x Jika Batal) : ");
        String name = readLine();
        if (isCancel(name)) return;

        System.out.print("Tujuan Kunjungan (x Jika Batal) : ");
        String purpose = readLine();
        if (isCancel(purpose)) return;

        Guest g = useCase.register(name, purpose);
        System.out.println(presenter.registered(g));
    }

    private void search() {
        System.out.println("[Mencari Tamu]");
        System.out.print("Nama (x Jika Batal) : ");
        String keyword = readLine();
        if (isCancel(keyword)) return;

        System.out.println(presenter.searchHeader(keyword));
        List<Guest> result = useCase.search(keyword);
        if (result.isEmpty()) {
            System.out.println(presenter.notFound());
        } else {
            for (Guest g : result) {
                System.out.println(presenter.formatGuest(g));
            }
        }
    }

    private void delete() {
        System.out.println("[Menghapus Tamu]");
        System.out.print("[ID Tamu] yang dihapus (x Jika Batal) : ");
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
