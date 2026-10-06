package adapter.presenter;

import domain.entity.Activity;
import java.util.List;

public class ActivityPresenter {

    public String formatActivity(Activity a) {
        return "[" + a.getId() + "] " + a.getTitle() + " | Hari: " + a.getDay()
                + " | Pukul: " + a.getTime();
    }

    public String formatList(String title, List<Activity> list) {
        StringBuilder sb = new StringBuilder(title).append("\n");
        if (list.isEmpty()) {
            sb.append("- Data kegiatan belum tersedia!\n");
        } else {
            for (Activity a : list) {
                sb.append(formatActivity(a)).append("\n");
            }
        }
        return sb.toString();
    }

    public String formatDashboard(List<Activity> list) {
        return formatList("Daftar Kegiatan:", list)
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
                + "1. Hari (Senin -> Minggu)\n"
                + "2. Waktu (Awal -> Akhir)\n"
                + "3. Judul (A-Z)\n"
                + "4. Judul (Z-A)\n"
                + "x. Batal\n";
    }

    public String added(Activity a) { return "Berhasil menambah kegiatan: " + formatActivity(a); }
    public String searchHeader(String keyword) { return "Hasil Pencarian: \"" + keyword + "\""; }
    public String notFound() { return "- Kegiatan tidak ditemukan!"; }
    public String updated() { return "Berhasil mengubah kegiatan."; }
    public String updateFailed(int id) { return "[!] Gagal mengubah kegiatan dengan ID: " + id + "."; }
    public String deleted() { return "Berhasil menghapus kegiatan."; }
    public String deleteFailed(int id) { return "[!] Gagal menghapus kegiatan dengan ID: " + id + "."; }
    public String invalidId() { return "[!] ID tidak valid!"; }
    public String invalidMenu() { return "[!] Pilihan tidak dimengerti."; }
    public String invalidSort() { return "[!] Pilihan tidak valid!"; }
}
