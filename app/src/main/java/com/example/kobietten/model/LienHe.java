package com.example.kobietten.model;

import java.util.HashMap;
import java.util.Map;

public class LienHe {
    private String soDienThoai;
    private String email;

    public LienHe() {
        // Firebase cần có constructor mặc định
    }

    public LienHe(String soDienThoai, String email) {
        this.soDienThoai = soDienThoai;
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Định dạng thông tin liên hệ thành Map để dễ dàng lưu vào Firebase
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("soDienThoai", soDienThoai);
        result.put("email", email);
        return result;
    }
}
