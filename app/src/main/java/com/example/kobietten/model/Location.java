package com.example.kobietten.model;

public class Location {
    private String name;
    private String code; // detail có thể là mã sân bay hoặc thông tin khác

    // Constructor mặc định cần thiết cho Firebase
    public Location() {
    }

    public Location(String name, String code) {
        this.name = name;
        this.code = code;
    }

    // Getter và Setter
    public void setName(String name){
        this.name = name;
    }

    public void setCode(String code){
        this.code = code;
    }
    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}

