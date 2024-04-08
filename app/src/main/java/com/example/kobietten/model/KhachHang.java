package com.example.kobietten.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class KhachHang implements Serializable {
    private String hoten;
    private String namsinh;

    public KhachHang() {
    }

    public KhachHang(String hoten, String namsinh) {
        this.hoten = hoten;
        this.namsinh = namsinh;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getNamsinh() {
        return namsinh;
    }

    public void setNamsinh(String namsinh) {
        this.namsinh = namsinh;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Họ tên", hoten);
        result.put("Năm sinh", namsinh);
        return result;
    }

}
