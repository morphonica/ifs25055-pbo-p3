package framework.view;

import adapter.presenter.FinancePresenter;
import domain.entity.Transaction;
import domain.entity.TransactionType;
import java.util.List;
import java.util.Scanner;
import usecase.FinanceUseCase;
import usecase.SortMode;

public class FinanceView {
    private final FinanceUseCase useCase;
    private final FinancePresenter presenter;
    private final Scanner in = new Scanner(System.in);

    public FinanceView(FinanceUseCase useCase, FinancePresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        boolean first = true;
        while (true) {
            if (!first) System.out.println();
            first = false;

            System.out.print(presenter.formatDashboard(useCase.getAll(), useCase.getBalance()));
            String choice = readChoice();
            if (choice == null || choice.equalsIgnoreCase("x")) break;

            switch (choice) {
                case "1": addTransaction("[Tambah Pemasukan]", TransactionType.INCOME); break;
                case "2": addTransaction("[Tambah Pengeluaran]", TransactionType.EXPENSE); break;
                case "3": search(); break;
                case "4": sort(); break;
                case "5": System.out.println(presenter.balance(useCase.getBalance())); break;
                case "6": delete(); break;
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

    private void addTransaction(String title, TransactionType type) {
        System.out.println(title);
        System.out.print("Keterangan (x Jika Batal) : ");
        String desc = readLine();
        if (isCancel(desc)) return;

        System.out.print("Jumlah : ");
        String amountStr = readLine();
        try {
            long amount = Long.parseLong(amountStr);
            Transaction t = useCase.addTransaction(desc, amount, type);
            System.out.println(presenter.added(t));
        } catch (IllegalArgumentException e) { // termasuk NumberFormatException
            System.out.println(presenter.invalidAmount());
        }
    }

    private void search() {
        System.out.println("[Cari Transaksi]");
        System.out.print("Kata Kunci (x Jika Batal) : ");
        String keyword = readLine();
        if (isCancel(keyword)) return;

        System.out.println(presenter.searchHeader(keyword));
        List<Transaction> result = useCase.search(keyword);
        if (result.isEmpty()) {
            System.out.println(presenter.notFound());
        } else {
            for (Transaction t : result) {
                System.out.println(presenter.formatTransaction(t));
            }
        }
    }

    private void sort() {
        System.out.println("[Urutkan Transaksi]");
        System.out.print(presenter.sortMenu());
        String choice = readChoice();
        if (isCancel(choice)) return;

        SortMode mode;
        switch (choice) {
            case "1": mode = SortMode.AMOUNT_ASC; break;
            case "2": mode = SortMode.AMOUNT_DESC; break;
            case "3": mode = SortMode.INCOME_FIRST; break;
            case "4": mode = SortMode.EXPENSE_FIRST; break;
            default:
                System.out.println(presenter.invalidSort());
                return;
        }
        System.out.print(presenter.formatList("Daftar Transaksi (Terurut):", useCase.sort(mode)));
    }

    private void delete() {
        System.out.println("[Hapus Transaksi]");
        System.out.print("ID Transaksi (x Jika Batal) : ");
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
