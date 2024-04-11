package com.example.kobietten.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kobietten.R;
import com.example.kobietten.model.KhachHang;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThanhtoanActivity extends AppCompatActivity {
    // Khai báo DatabaseReference
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Khởi tạo DatabaseReference
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Truy xuất dữ liệu từ Intent
        Intent intent = getIntent();
        String diemDi = intent.getStringExtra("PUT_DIEMDI");
        String diemDen = intent.getStringExtra("PUT_DIEMDEN");
        String ngayDi = intent.getStringExtra("PUT_NGAYDI");
        String dienThoai = intent.getStringExtra("PUT_DIENTHOAI");
        String email = intent.getStringExtra("PUT_EMAIL");
        ArrayList<KhachHang> danhSachKhachHang = (ArrayList<KhachHang>) intent.getSerializableExtra("EXTRA_DANH_SACH_KHACH_HANG");

        // Xử lý sự kiện nhấn nút thanh toán
        Button btnThanhToan = findViewById(R.id.btnThanhtoan);
        btnThanhToan.setOnClickListener(v -> thanhToan(diemDi, diemDen, ngayDi, dienThoai, email, danhSachKhachHang));
    }

    private void thanhToan(String diemDi, String diemDen, String ngayDi, String dienThoai, String email, ArrayList<KhachHang> danhSachKhachHang) {
        // Tạo ID duy nhất cho mỗi lần đặt vé
        String bookingId = mDatabase.child("Đặt vé").push().getKey();

        // Tạo cấu trúc lưu trữ dữ liệu
        Map<String, Object> bookingData = new HashMap<>();
        bookingData.put("Ngày/Ngày đi", ngayDi);
        bookingData.put("Liên hệ/Số điện thoại", dienThoai);
        bookingData.put("Liên hệ/Email", email);
        bookingData.put("Chuyến bay/Điểm đi", diemDi);
        bookingData.put("Chuyến bay/Điểm đến", diemDen);

        // Lặp qua danh sách khách hàng và thêm vào bookingData
        Map<String, Object> customerData = new HashMap<>();
        for (int i = 0; i < danhSachKhachHang.size(); i++) {
            KhachHang khachHang = danhSachKhachHang.get(i);
            customerData.put("Khách hàng_" + i+1, khachHang.toMap());
        }
        bookingData.put("Khách hàng", customerData);

        // Lưu dữ liệu vào Firebase
        assert bookingId != null;
        mDatabase.child("Đặt vé").child(bookingId).updateChildren(bookingData)
                .addOnSuccessListener(aVoid -> {
                    // Xử lý thành công, chuyển về màn hình chính hoặc hiển thị thông báo
                    Toast.makeText(ThanhtoanActivity.this, "Thanh toán thành công!", Toast.LENGTH_LONG).show();
                    // Chuyển hướng người dùng về màn hình chính hoặc thông báo
                })
                .addOnFailureListener(e -> {
                    // Xử lý thất bại, hiển thị thông báo lỗi
                    Toast.makeText(ThanhtoanActivity.this, "Thanh toán thất bại: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
