package adapter.presenter;

import domain.entity.Item;
import java.util.List;

public class ItemPresenter {

    public String formatItem(Item i) {
        return "[" + i.getId() + "] " + i.getName() + " | Stok: " + i.getStock()
                + " | Kategori: " + i.getCategory();
    }

    public String formatList(String title, List<Item> list) {
        StringBuilder sb = new StringBuilder(title).append("\n");
        if (list.isEmpty()) {
            sb.append("- Data barang belum tersedia!\n");
        } else {
            for (Item i : list) {
                sb.append(formatItem(i)).append("\n");
            }
        }
        return sb.toString();
    }

    public String formatDashboard(List<Item> list) {
        return formatList("Daftar Barang:", list)
                + "Menu:\n"
                + "1. Tambah\n"
                + "2. Ubah Stok\n"
                + "3. Cari\n"
                + "4. Urutkan\n"
                + "5. Hapus\n"
                + "x. Keluar\n";
    }

    public String sortMenu() {
        return "Pilihan Pengurutan:\n"
                + "1. Nama (A-Z)\n"
                + "2. Nama (Z-A)\n"
                + "3. Jumlah (Terkecil -> Terbesar)\n"
                + "4. Jumlah (Terbesar -> Terkecil)\n"
                + "x. Batal\n";
    }

    public String added(Item i) { return "Berhasil menambah barang: " + formatItem(i); }
    public String searchHeader(String keyword) { return "Hasil Pencarian: \"" + keyword + "\""; }
    public String notFound() { return "- Barang tidak ditemukan!"; }
    public String updated() { return "Berhasil mengubah stok barang."; }
    public String updateFailed(int id) { return "[!] Gagal mengubah stok barang dengan ID: " + id + "."; }
    public String deleted() { return "Berhasil menghapus barang."; }
    public String deleteFailed(int id) { return "[!] Gagal menghapus barang dengan ID: " + id + "."; }
    public String invalidStock() { return "[!] Jumlah stok tidak valid!"; }
    public String invalidId() { return "[!] ID tidak valid!"; }
    public String invalidMenu() { return "[!] Pilihan tidak dimengerti."; }
    public String invalidSort() { return "[!] Pilihan tidak valid!"; }
}
