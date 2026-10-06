package framework.view;

import adapter.presenter.ItemPresenter;
import domain.entity.Item;
import java.util.List;
import java.util.Scanner;
import usecase.ItemUseCase;
import usecase.SortMode;

public class ItemView {
    private final ItemUseCase useCase;
    private final ItemPresenter presenter;
    private final Scanner in = new Scanner(System.in);

    public ItemView(ItemUseCase useCase, ItemPresenter presenter) {
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
                case "1": addItem(); break;
                case "2": updateStock(); break;
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

    private void addItem() {
        System.out.println("[Menambah Barang]");
        System.out.print("Nama (x Jika Batal) : ");
        String name = readLine();
        if (isCancel(name)) return;

        System.out.print("Jumlah : ");
        String stockStr = readLine();
        long stock;
        try {
            stock = Long.parseLong(stockStr);
            if (stock < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println(presenter.invalidStock());
            return;
        }

        System.out.print("Kategori (x Jika Batal) : ");
        String category = readLine();
        if (isCancel(category)) return;

        Item item = useCase.addItem(name, stock, category);
        System.out.println(presenter.added(item));
    }

    private void updateStock() {
        System.out.println("[Mengubah Stok]");
        System.out.print("ID Barang yang diubah (x Jika Batal) : ");
        String idStr = readLine();
        if (isCancel(idStr)) return;

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.out.println(presenter.invalidId());
            return;
        }

        System.out.print("Jumlah Baru (Kosongkan jika tidak ingin mengubah) : ");
        String stockStr = readLine();
        if (stockStr == null) return;

        Long newStock = null;
        if (!stockStr.isEmpty()) {
            try {
                newStock = Long.parseLong(stockStr);
                if (newStock < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println(presenter.invalidStock());
                return;
            }
        }

        if (useCase.updateStock(id, newStock)) {
            System.out.println(presenter.updated());
        } else {
            System.out.println(presenter.updateFailed(id));
        }
    }

    private void search() {
        System.out.println("[Mencari Barang]");
        System.out.print("Kata Kunci (x Jika Batal) : ");
        String keyword = readLine();
        if (isCancel(keyword)) return;

        System.out.println(presenter.searchHeader(keyword));
        List<Item> result = useCase.search(keyword);
        if (result.isEmpty()) {
            System.out.println(presenter.notFound());
        } else {
            for (Item i : result) {
                System.out.println(presenter.formatItem(i));
            }
        }
    }

    private void sort() {
        System.out.println("[Mengurutkan Barang]");
        System.out.print(presenter.sortMenu());
        String choice = readChoice();
        if (isCancel(choice)) return;

        SortMode mode;
        switch (choice) {
            case "1": mode = SortMode.NAME_ASC; break;
            case "2": mode = SortMode.NAME_DESC; break;
            case "3": mode = SortMode.STOCK_ASC; break;
            case "4": mode = SortMode.STOCK_DESC; break;
            default:
                System.out.println(presenter.invalidSort());
                return;
        }
        System.out.print(presenter.formatList("Daftar Barang (Terurut):", useCase.sort(mode)));
    }

    private void delete() {
        System.out.println("[Menghapus Barang]");
        System.out.print("[ID Barang] yang dihapus (x Jika Batal) : ");
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
