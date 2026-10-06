package adapter.presenter;

import domain.entity.Transaction;
import java.util.List;

public class FinancePresenter {

    public String formatTransaction(Transaction t) {
        return "[" + t.getId() + "] " + t.getDescription() + " | Rp " + t.getAmount()
                + " | [" + (t.isIncome() ? "Pemasukan" : "Pengeluaran") + "]";
    }

    public String formatList(String title, List<Transaction> list) {
        StringBuilder sb = new StringBuilder(title).append("\n");
        if (list.isEmpty()) {
            sb.append("- Belum ada transaksi!\n");
        } else {
            for (Transaction t : list) {
                sb.append(formatTransaction(t)).append("\n");
            }
        }
        return sb.toString();
    }

    public String formatDashboard(List<Transaction> list, long balance) {
        return formatList("Daftar Transaksi:", list)
                + "Saldo: Rp " + balance + "\n"
                + "Menu:\n"
                + "1. Tambah Pemasukan\n"
                + "2. Tambah Pengeluaran\n"
                + "3. Cari\n"
                + "4. Urutkan\n"
                + "5. Lihat Saldo\n"
                + "6. Hapus\n"
                + "x. Keluar\n";
    }

    public String sortMenu() {
        return "1. Jumlah (Terkecil)\n"
                + "2. Jumlah (Terbesar)\n"
                + "3. Pemasukan Dulu\n"
                + "4. Pengeluaran Dulu\n"
                + "x. Batal\n";
    }

    public String added(Transaction t) {
        return "Berhasil menambah transaksi: " + formatTransaction(t);
    }

    public String searchHeader(String keyword) {
        return "Hasil Pencarian: \"" + keyword + "\"";
    }

    public String notFound() { return "- Transaksi tidak ditemukan!"; }
    public String balance(long balance) { return "Saldo saat ini: Rp " + balance; }
    public String deleted() { return "Berhasil menghapus transaksi."; }
    public String deleteFailed(int id) { return "[!] Gagal menghapus transaksi dengan ID: " + id + "."; }
    public String invalidAmount() { return "[!] Jumlah tidak valid!"; }
    public String invalidId() { return "[!] ID tidak valid!"; }
    public String invalidMenu() { return "[!] Pilihan tidak dimengerti."; }
    public String invalidSort() { return "[!] Pilihan tidak valid!"; }
}
