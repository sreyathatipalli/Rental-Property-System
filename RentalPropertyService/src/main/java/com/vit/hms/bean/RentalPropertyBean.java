package com.vit.hms.bean;
public class RentalPropertyBean {
    private int rentalAmount;
    private int noOfBedRooms;
    private String location;
    private String city;
    private String propertyId;
    public int getRentalAmount() {
        return rentalAmount;
    }
    public void setRentalAmount(int rentalAmount) {
        this.rentalAmount = rentalAmount;
    }
    public int getNoOfBedRooms() {
        return noOfBedRooms;
    }
    public void setNoOfBedRooms(int noOfBedRooms) {
        this.noOfBedRooms = noOfBedRooms;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getPropertyId() {
        return propertyId;
    }
    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }
}