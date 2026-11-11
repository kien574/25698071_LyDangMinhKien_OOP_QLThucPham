package LyDangMinhKien25698071;
import java.time.LocalDate;

public class QuanLyThucPham {
    // ---------- Thuộc tính ----------
    private HangThucPham[] danhSach; // Mảng nguyên thủy
    private int soLuong;             // Số phần tử hiện có
    private int khaNang;             // Sức chứa hiện tại

    private static final int KICH_THUOC_BAN_DAU = 5;

    // ---------- Constructor ----------
    public QuanLyThucPham() {
        this.khaNang = KICH_THUOC_BAN_DAU;
        this.danhSach = new HangThucPham[khaNang];
        this.soLuong = 0;
    }

    // =========== MỞ RỘNG MẢNG ===========
    private void moRongMang() {
        int kichThuocMoi = khaNang * 2;
        HangThucPham[] mangMoi = new HangThucPham[kichThuocMoi];
        for (int i = 0; i < soLuong; i++) {
            mangMoi[i] = danhSach[i];
        }
        danhSach = mangMoi;
        khaNang = kichThuocMoi;
        System.out.println("🔄 Đã mở rộng mảng lên " + khaNang + " phần tử.");
    }

    // =========== TÌM VỊ TRÍ THEO MÃ ===========
    public int timViTriTheoMa(String maHang) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMaHang().equalsIgnoreCase(maHang))
                return i;
        }
        return -1;
    }

    // =========== TÌM VỊ TRÍ THEO ID ===========
    public int timViTriTheoId(int id) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getId() == id) return i;
        }
        return -1;
    }

    // =========== THÊM (chống trùng + tự mở rộng) ===========
    public boolean them(HangThucPham h) {
    	if (h == null) {
            System.out.println("Không thể thêm sản phẩm null!");
            return false;
        }
        if (timViTriTheoMa(h.getMaHang()) != -1) {
            System.out.println("Lỗi: Mã hàng '" + h.getMaHang() + "' đã tồn tại!");
            return false;
        }
        if (soLuong == khaNang) moRongMang();
        danhSach[soLuong] = h;
        soLuong++;
        return true;
    }

    // =========== XÓA THEO ID ===========
    public boolean xoa(int id) {
        int viTri = timViTriTheoId(id);
        if (viTri == -1) {
            System.out.println("Không tìm thấy hàng có id " + id);
            return false;
        }
        for (int i = viTri; i < soLuong - 1; i++) {
            danhSach[i] = danhSach[i + 1];
        }
        danhSach[soLuong - 1] = null;
        soLuong--;
        return true;
    }

    // =========== SỬA THEO ID ===========
    public boolean sua(int id, String tenMoi, LocalDate ngaySXMoi,
                       LocalDate ngayHHMoi, float giaMoi) {
        int viTri = timViTriTheoId(id);
        if (viTri == -1) {
            System.out.println("Không tìm thấy hàng có id " + id);
            return false;
        }
        try {
            if (tenMoi == null || tenMoi.trim().isEmpty()) {
                throw new IllegalArgumentException("Tên hàng không được rỗng!");
            }

            if (ngaySXMoi == null || ngaySXMoi.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Ngày SX không được tương lai!");
            }

            if (ngayHHMoi == null || !ngayHHMoi.isAfter(ngaySXMoi)) {
                throw new IllegalArgumentException("Ngày HH phải sau ngày SX!");
            }

            if (giaMoi <= 0) {
                throw new IllegalArgumentException("Giá phải lớn hơn 0!");
            }

            HangThucPham h = danhSach[viTri];

            h.setTenHang(tenMoi);
            h.setNgaySanXuat(ngaySXMoi);
            h.setNgayHetHan(ngayHHMoi);
            h.setGia(giaMoi);

            return true;

        } catch (IllegalArgumentException e) {
            System.out.println("Lỗi: " + e.getMessage());
            return false;
        }
    }

    // =========== TÍNH TOÁN & THỐNG KÊ ===========
    public int laysoLuong() {
        return this.soLuong;
    }

    public int tinhTongSoLuong() {
        return this.soLuong;
    }

    public double tinhTongGiaTri() {
        double tong = 0;
        for (int i = 0; i < soLuong; i++) {
            tong += danhSach[i].getGia();
        }
        return tong;
    }

    public void thongKeTheoThang() {
        System.out.println("\n--- Thống kê theo tháng sản xuất ---");
        int[] dem = new int[13];
        for (int i = 0; i < soLuong; i++) {
            int thang = danhSach[i].getNgaySanXuat().getMonthValue();
            dem[thang]++;
        }
        for (int t = 1; t <= 12; t++) {
            if (dem[t] > 0) {
                System.out.println("Tháng " + t + ": " + dem[t] + " sản phẩm");
            }
        }
    }

    // =========== HIỂN THỊ DANH SÁCH ===========
    public void hienThiDanhSach() {
        if (soLuong == 0) {
            System.out.println("⚠️ Danh sách trống!");
            return;
        }
        System.out.println("+------+------------+--------------------+--------------+--------------+------------+");
        System.out.println("| ID   | Mã hàng    | Tên hàng           | Ngày SX      | Ngày HH      | Giá        |");
        System.out.println("+------+------------+--------------------+--------------+--------------+------------+");
        for (int i = 0; i < soLuong; i++) {
            System.out.println(danhSach[i]);
        }
        System.out.println("+------+------------+--------------------+--------------+--------------+------------+");
    }

    // =========== SẮP XẾP ===========
    public void sapXepTheoGia(boolean tangDan) {
        for (int i = 0; i < soLuong - 1; i++) {
            for (int j = i + 1; j < soLuong; j++) {
                boolean dieuKien = tangDan ? danhSach[i].getGia() > danhSach[j].getGia()
                                           : danhSach[i].getGia() < danhSach[j].getGia();
                if (dieuKien) {
                    HangThucPham temp = danhSach[i];
                    danhSach[i] = danhSach[j];
                    danhSach[j] = temp;
                }
            }
        }
    }

    public void sapXepTheoNgaySanXuat(boolean tangDan) {
        for (int i = 0; i < soLuong - 1; i++) {
            for (int j = i + 1; j < soLuong; j++) {
                boolean dieuKien = tangDan ? danhSach[i].getNgaySanXuat().isAfter(danhSach[j].getNgaySanXuat())
                                           : danhSach[i].getNgaySanXuat().isBefore(danhSach[j].getNgaySanXuat());
                if (dieuKien) {
                    HangThucPham temp = danhSach[i];
                    danhSach[i] = danhSach[j];
                    danhSach[j] = temp;
                }
            }
        }
    }

    public void sapXepTheoNgayHetHan(boolean tangDan) {
        for (int i = 0; i < soLuong - 1; i++) {
            for (int j = i + 1; j < soLuong; j++) {
                boolean dieuKien = tangDan ? danhSach[i].getNgayHetHan().isAfter(danhSach[j].getNgayHetHan())
                                           : danhSach[i].getNgayHetHan().isBefore(danhSach[j].getNgayHetHan());
                if (dieuKien) {
                    HangThucPham temp = danhSach[i];
                    danhSach[i] = danhSach[j];
                    danhSach[j] = temp;
                }
            }
        }
    }

    // =========== TÌM KIẾM ===========
    public QuanLyThucPham timTheoTienTo(String tk) {
        QuanLyThucPham kq = new QuanLyThucPham();
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getTenHang().toLowerCase().startsWith(tk.toLowerCase())) {
                kq.them(danhSach[i]);
            }
        }
        return kq;
    }

    public QuanLyThucPham timTheoHauTo(String tk) {
        QuanLyThucPham kq = new QuanLyThucPham();
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getTenHang().toLowerCase().endsWith(tk.toLowerCase())) {
                kq.them(danhSach[i]);
            }
        }
        return kq;
    }

    public QuanLyThucPham timGanGiong(String tk) {
        QuanLyThucPham kq = new QuanLyThucPham();
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getTenHang().toLowerCase().contains(tk.toLowerCase())) {
                kq.them(danhSach[i]);
            }
        }
        return kq;
    }

    // =========== TRÍCH LỌC THEO NGÀY ===========
    public QuanLyThucPham trichLocTheoNgaySanXuat(LocalDate tu, LocalDate den) {
        QuanLyThucPham kq = new QuanLyThucPham();
        for (int i = 0; i < soLuong; i++) {
            LocalDate nsx = danhSach[i].getNgaySanXuat();
            if ((nsx.isEqual(tu) || nsx.isAfter(tu)) && (nsx.isEqual(den) || nsx.isBefore(den))) {
                kq.them(danhSach[i]);
            }
        }
        return kq;
    }

    public QuanLyThucPham trichLocTheoNgayHetHan(LocalDate tu, LocalDate den) {
        QuanLyThucPham kq = new QuanLyThucPham();
        for (int i = 0; i < soLuong; i++) {
            LocalDate nhh = danhSach[i].getNgayHetHan();
            if ((nhh.isEqual(tu) || nhh.isAfter(tu)) && (nhh.isEqual(den) || nhh.isBefore(den))) {
                kq.them(danhSach[i]);
            }
        }
        return kq;
    }
}