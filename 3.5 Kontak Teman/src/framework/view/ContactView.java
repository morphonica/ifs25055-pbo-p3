package framework.view;

import adapter.presenter.ContactPresenter;
import domain.entity.Contact;
import java.util.List;
import java.util.Scanner;
import usecase.ContactUseCase;
import usecase.SortMode;

public class ContactView {
    private final ContactUseCase useCase;
    private final ContactPresenter presenter;
    private final Scanner in = new Scanner(System.in);

    public ContactView(ContactUseCase useCase, ContactPresenter presenter) {
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
                case "1": addContact(); break;
                case "2": updateContact(); break;
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

    private void addContact() {
        System.out.println("[Menambah Kontak]");
        System.out.print("Nama (x Jika Batal) : ");
        String name = readLine();
        if (isCancel(name)) return;

        System.out.print("Telepon : ");
        String phone = readLine();
        if (phone == null) return;

        System.out.print("Email : ");
        String email = readLine();
        if (email == null) return;

        Contact c = useCase.addContact(name, phone, email);
        System.out.println(presenter.added(c));
    }

    private void updateContact() {
        System.out.println("[Mengubah Kontak]");
        System.out.print("ID Kontak yang diubah (x Jika Batal) : ");
        String idStr = readLine();
        if (isCancel(idStr)) return;

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.out.println(presenter.invalidId());
            return;
        }

        System.out.print("Nama Baru (Kosongkan jika tidak ingin mengubah) : ");
        String name = readLine();
        System.out.print("Telepon Baru (Kosongkan jika tidak ingin mengubah) : ");
        String phone = readLine();
        System.out.print("Email Baru (Kosongkan jika tidak ingin mengubah) : ");
        String email = readLine();

        if (useCase.updateContact(id, name, phone, email)) {
            System.out.println(presenter.updated());
        } else {
            System.out.println(presenter.updateFailed(id));
        }
    }

    private void search() {
        System.out.println("[Mencari Kontak]");
        System.out.print("Kata Kunci (x Jika Batal) : ");
        String keyword = readLine();
        if (isCancel(keyword)) return;

        System.out.println(presenter.searchHeader(keyword));
        List<Contact> result = useCase.search(keyword);
        if (result.isEmpty()) {
            System.out.println(presenter.notFound());
        } else {
            for (Contact c : result) {
                System.out.println(presenter.formatContact(c));
            }
        }
    }

    private void sort() {
        System.out.println("[Mengurutkan Kontak]");
        System.out.print(presenter.sortMenu());
        String choice = readChoice();
        if (isCancel(choice)) return;

        SortMode mode;
        switch (choice) {
            case "1": mode = SortMode.NAME_ASC; break;
            case "2": mode = SortMode.NAME_DESC; break;
            default:
                System.out.println(presenter.invalidSort());
                return;
        }
        System.out.print(presenter.formatList("Daftar Kontak (Terurut):", useCase.sort(mode)));
    }

    private void delete() {
        System.out.println("[Menghapus Kontak]");
        System.out.print("[ID Kontak] yang dihapus (x Jika Batal) : ");
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
