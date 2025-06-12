package com.CarDealership.contract;

import com.CarDealership.model.Vehicle;

public abstract class Contract {

    protected String date;
    protected String name;
    protected String email;
    protected Vehicle vehicle;
    protected double totalPrice;
    protected double monthlyPayment;

    public Contract(String date, String name, String email, Vehicle vehicle) {
        this.date = date;
        this.name = name;
        this.email = email;
        this.vehicle = vehicle;
        this.totalPrice = 0;
        this.monthlyPayment = 0;
    }

    public Contract() {
        this.date = "";
        this.name = "";
        this.email = "";
        this.vehicle = new Vehicle();
        this.totalPrice = 0;
        this.monthlyPayment = 0;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    //abstract methods below
    public abstract double getTotalPrice();
    public abstract double getMonthlyPayment();

}
