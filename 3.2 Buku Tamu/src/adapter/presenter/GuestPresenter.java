package adapter.presenter;

import domain.entity.Guest;
import java.util.List;

public class GuestPresenter {

    public String formatGuest(Guest g) {
        return "[" + g.getId() + "] " + g.getName() + " | Tujuan: " + g.getPurpose();
    }

    public String formatDashboard(List<Guest> list) {
        StringBuilder sb = new StringBuilder("Daftar Tamu:\n");
        if (list.isEmpty()) {
            sb.append("- Data tamu belum tersedia!\n");
        } else {
            for (Guest g : list) {
                sb.append(formatGuest(g)).append("\n");
            }
        }
        sb.append("Menu:\n")
          .append("1. Daftarkan\n")
          .append("2. Cari\n")
          .append("3. Hapus\n")
          .append("x. Keluar\n");
        return sb.toString();
    }

    public String registered(Guest g) { return "Berhasil mendaftarkan tamu: " + formatGuest(g); }
    public String searchHeader(String keyword) { return "Hasil Pencarian: \"" + keyword + "\""; }
    public String notFound() { return "- Tamu tidak ditemukan!"; }
    public String deleted() { return "Berhasil menghapus tamu."; }
    public String deleteFailed(int id) { return "[!] Gagal menghapus tamu dengan ID: " + id + "."; }
    public String invalidId() { return "[!] ID tidak valid!"; }
    public String invalidMenu() { return "[!] Pilihan tidak dimengerti."; }
}
