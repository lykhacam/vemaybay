package com.example.kobietten.model;

public class SanBay {
    private String airportCode;
    private String cityName;
    public SanBay() {
    }

    public SanBay(String airportCode, String cityName) {
        this.airportCode = airportCode;
        this.cityName = cityName;
    }

    // Getters và Setters
    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
