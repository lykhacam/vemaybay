package com.example.kobietten.model;
import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

// Lớp này định nghĩa cấu trúc dữ liệu cho việc thanh toán
public class ThanhToan {
    // Thông tin liên hệ của người dùng
    private LienHe lienHe;
    // Danh sách khách hàng dưới dạng bản đồ, với key là ID khách hàng
    private HashMap<String, KhachHang> danhSachKhachHang;
    // Điểm đi, điểm đến và ngày đi của chuyến bay
    private String diemDi;
    private String diemDen;
    private String ngayDi;

    // Constructor mặc định cần thiết cho Firebase khi lấy dữ liệu
    public ThanhToan() {
        // Khởi tạo danh sách khách hàng
        this.danhSachKhachHang = new HashMap<>();
    }

    // Constructor với tham số để tạo một đối tượng ThanhToan mới
    public ThanhToan(LienHe lienHe, HashMap<String, KhachHang> danhSachKhachHang, String diemDi, String diemDen, String ngayDi) {
        this.lienHe = lienHe;
        this.danhSachKhachHang = danhSachKhachHang;
        this.diemDi = diemDi;
        this.diemDen = diemDen;
        this.ngayDi = ngayDi;
    }

    // Getter và Setter cho các trường dữ liệu

    // Chuyển đổi đối tượng này thành một Map để dễ dàng lưu trữ trong Firebase
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("lienHe", lienHe.toMap()); // Giả định rằng LienHe cũng có phương thức toMap()
        result.put("danhSachKhachHang", danhSachKhachHang);
        result.put("diemDi", diemDi);
        result.put("diemDen", diemDen);
        result.put("ngayDi", ngayDi);
        return result;
    }

    // Phương thức để thêm một khách hàng vào danh sách
    public void themKhachHang(String khachHangId, KhachHang khachHang) {
        this.danhSachKhachHang.put(khachHangId, khachHang);
    }


}
