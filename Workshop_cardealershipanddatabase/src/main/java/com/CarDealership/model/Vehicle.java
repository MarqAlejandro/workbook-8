package com.CarDealership.model;

public class Vehicle {                                                               //vehicle class to hold all vehicle data

    private int vin;                                                                //variables are set
    private int year;
    private String make;
    private String model;
    private String vehicleType;
    private String color;
    private int odometer;
    private double price;

    public Vehicle(int vin, int year, String make,                                 //constructors to initialize the variables
                   String model, String vehicleType,
                   String color, int odometer, double price) {
        this.vin = vin;
        this.year = year;
        this.make = make;
        this.model = model;
        this.vehicleType = vehicleType;
        this.color = color;
        this.odometer = odometer;
        this.price = price;
    }

    public Vehicle() {                                                  //empty constructor
        this.vin = 0;
        this.year = 0;
        this.make = "";
        this.model = "";
        this.vehicleType = "";
        this.color = "";
        this.odometer = 0;
        this.price = 0;

    }

    public Vehicle(int vin, double price) {
        this.vin = vin;
        this.price = price;
    }

    public void display(){                                          //display all vehicles information
        System.out.println("VIN: " + getVin() +
                "| Year: " + getYear() +
                "| Make: " + getMake() +
                "| Model: " + getModel() +
                "| Vehicle Type: " + getVehicleType() +
                "| Color: " + getColor() +
                "| Odometer: " + getOdometer() +
                "| Price: " + getPrice());
    }

    public int getVin() {                                           //getters and setters
        return vin;
    }

    public void setVin(int vin) {
        this.vin = vin;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
