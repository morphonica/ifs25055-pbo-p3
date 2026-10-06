package adapter.presenter;

import domain.entity.Contact;
import java.util.List;

public class ContactPresenter {

    public String formatContact(Contact c) {
        return "[" + c.getId() + "] " + c.getName() + " | Telp: " + c.getPhone()
                + " | Email: " + c.getEmail();
    }

    public String formatList(String title, List<Contact> list) {
        StringBuilder sb = new StringBuilder(title).append("\n");
        if (list.isEmpty()) {
            sb.append("- Data kontak belum tersedia!\n");
        } else {
            for (Contact c : list) {
                sb.append(formatContact(c)).append("\n");
            }
        }
        return sb.toString();
    }

    public String formatDashboard(List<Contact> list) {
        return formatList("Daftar Kontak:", list)
                + "Menu:\n"
                + "1. Tambah\n"
                + "2. Ubah\n"
                + "3. Cari\n"
                + "4. Urutkan\n"
                + "5. Hapus\n"
                + "x. Keluar\n";
    }

    public String sortMenu() {
        return "Pilihan Pengurutan:\n"
                + "1. Nama (A-Z)\n"
                + "2. Nama (Z-A)\n"
                + "x. Batal\n";
    }

    public String added(Contact c) { return "Berhasil menambah kontak: " + formatContact(c); }
    public String searchHeader(String keyword) { return "Hasil Pencarian: \"" + keyword + "\""; }
    public String notFound() { return "- Kontak tidak ditemukan!"; }
    public String updated() { return "Berhasil mengubah kontak."; }
    public String updateFailed(int id) { return "[!] Gagal mengubah kontak dengan ID: " + id + "."; }
    public String deleted() { return "Berhasil menghapus kontak."; }
    public String deleteFailed(int id) { return "[!] Gagal menghapus kontak dengan ID: " + id + "."; }
    public String invalidId() { return "[!] ID tidak valid!"; }
    public String invalidMenu() { return "[!] Pilihan tidak dimengerti."; }
    public String invalidSort() { return "[!] Pilihan tidak valid!"; }
}
