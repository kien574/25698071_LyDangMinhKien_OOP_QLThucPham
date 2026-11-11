package LyDangMinhKien25698071;

import java.time.LocalDate;

public class HangThucPham implements Comparable<HangThucPham> {
    private int id;
    private final String maHang; // Không cho phép sửa sau khi khởi tạo
    private String tenHang;
    private LocalDate ngaySanXuat;
    private LocalDate ngayHetHan;
    private float gia;

    private static int demSoLuong = 0;

    // ---------- Constructor mặc định ----------
    public HangThucPham() {
        this.id = ++demSoLuong;
        this.maHang = "MAC_DINH";
        this.tenHang = "Hàng mặc định";
        this.ngaySanXuat = LocalDate.now();
        this.ngayHetHan = LocalDate.now().plusDays(7);
        this.gia = 1.0f;
    }

    // ---------- Constructor có tham số ----------
	public HangThucPham(String maHang, String tenHang,
	            LocalDate ngaySanXuat, LocalDate ngayHetHan,
	            float gia) {
	
		if (maHang == null || maHang.trim().isEmpty())
		throw new IllegalArgumentException("Mã hàng không được để trống!");
		
		if (tenHang == null || tenHang.trim().isEmpty())
		throw new IllegalArgumentException("Tên hàng không được để trống!");
		
		if (gia <= 0)
		throw new IllegalArgumentException("Giá phải lớn hơn 0!");
		
		if (!kiemTraNgaySanXuat(ngaySanXuat))
		throw new IllegalArgumentException("Ngày SX không được là tương lai!");
		
		if (!kiemTraNgayHetHan(ngaySanXuat, ngayHetHan))
		throw new IllegalArgumentException("Ngày HH phải sau ngày SX!");
		
		this.id = ++demSoLuong;
		this.maHang = maHang;
		this.tenHang = tenHang;
		this.ngaySanXuat = ngaySanXuat;
		this.ngayHetHan = ngayHetHan;
		this.gia = gia;
	}


    // ---------- Kiểm tra ngày ----------
    private boolean kiemTraNgaySanXuat(LocalDate ngaySX) {
        return ngaySX != null && !ngaySX.isAfter(LocalDate.now());
    }

    private boolean kiemTraNgayHetHan(LocalDate ngaySX, LocalDate ngayHH) {
        return ngaySX != null && ngayHH != null && ngayHH.isAfter(ngaySX);
    }

    // ---------- Getter ----------
    public int getId() { return id; }
    public String getMaHang() { return maHang; }
    public String getTenHang() { return tenHang; }
    public LocalDate getNgaySanXuat() { return ngaySanXuat; }
    public LocalDate getNgayHetHan() { return ngayHetHan; }
    public float getGia() { return gia; }

    // ---------- Setter ----------
    // Lưu ý: KHÔNG có setMaHang() vì maHang là final, chỉ được gán khi khởi tạo.
    public void setTenHang(String tenHang) {
        if (tenHang == null || tenHang.trim().isEmpty())
            throw new IllegalArgumentException("Tên hàng không được rỗng!");
        this.tenHang = tenHang;
    }

    public void setNgaySanXuat(LocalDate ngaySanXuat) {
        if (!kiemTraNgaySanXuat(ngaySanXuat))
            throw new IllegalArgumentException("Ngày SX không được tương lai!");
        if (this.ngayHetHan != null && !kiemTraNgayHetHan(ngaySanXuat, this.ngayHetHan))
            throw new IllegalArgumentException("Ngày SX phải trước ngày HH!");
        this.ngaySanXuat = ngaySanXuat;
    }

    public void setNgayHetHan(LocalDate ngayHetHan) {
        if (this.ngaySanXuat == null)
            throw new IllegalArgumentException("Chưa có ngày SX!");
        if (!kiemTraNgayHetHan(this.ngaySanXuat, ngayHetHan))
            throw new IllegalArgumentException("Ngày HH phải sau ngày SX!");
        this.ngayHetHan = ngayHetHan;
    }

    public void setGia(float gia) {
        if (gia <= 0)
            throw new IllegalArgumentException("Giá phải lớn hơn 0!");
        this.gia = gia;
    }

    // ---------- Kiểm tra hết hạn ----------
    public boolean kiemTraHetHan() {
        return LocalDate.now().isAfter(this.ngayHetHan);
    }

    public String trangThaiChiTiet() {
        LocalDate homNay = LocalDate.now();
        if (homNay.isBefore(ngaySanXuat)) return "Chưa sản xuất";
        if (homNay.isAfter(ngayHetHan)) return "Hết hạn";
        if (homNay.isEqual(ngayHetHan)) return "Hết hạn hôm nay";
        long con = ngayHetHan.toEpochDay() - homNay.toEpochDay();
        return "Còn " + con + " ngày";
    }

    // ---------- Ngăn chặn clone ----------
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Không thể sao chép HangThucPham!");
    }

    @Override
    public int compareTo(HangThucPham o) {
        return Integer.compare(this.id, o.id);
    }

    @Override
    public String toString() {
        return String.format(
            "| %-4d | %-10s | %-18s | %-12s | %-12s | %-10.0f |",
            id, maHang, tenHang, ngaySanXuat, ngayHetHan, gia);
    }
}
