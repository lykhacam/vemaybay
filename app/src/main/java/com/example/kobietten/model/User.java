package com.example.kobietten.model;

import android.text.TextUtils;
import android.util.Patterns;

public class User {
    private String ten;
    private String diaChi;
    private String dienThoai;
    private String email;
    private String password;

    public User(String ten, String diaChi, String dienThoai, String email, String password) {
        this.ten = ten;
        this.diaChi = diaChi;
        this.dienThoai = dienThoai;
        this.email = email;
        this.password = password;
    }

    // Getters và Setters cho mỗi thuộc tính

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isValid() {
        return !TextUtils.isEmpty(ten) &&
                !TextUtils.isEmpty(diaChi) &&
                !TextUtils.isEmpty(dienThoai) &&
                !TextUtils.isEmpty(email) &&
                !TextUtils.isEmpty(password) &&
                dienThoai.matches("\\d{10}|\\d{11}")&&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()&&
                password.length() >= 6;
    }
}
