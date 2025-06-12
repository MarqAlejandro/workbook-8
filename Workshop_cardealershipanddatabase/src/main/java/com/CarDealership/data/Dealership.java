package com.CarDealership.data;

import com.CarDealership.model.Vehicle;

import java.util.List;

public class Dealership {

    private String name;
    private String address;
    private String phone;
    private static List<Vehicle> inventory = DealershipFileManager.getInventory();                  //gets list from the fileManager class

    public Dealership(String name, String address, String phone) {                                  //constructors
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
    public Dealership(){
        this.name = "";
        this.address = "";
        this.phone = "";
    }

    public String getName() {                                                                   //getters and setters
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void getVehiclesByPrice(double min,double max){                                              //for loop and print out vehicles within the range
        boolean isFound = false;
        for (Vehicle vehicle : inventory){
            if( (vehicle.getPrice() >= min) && (vehicle.getPrice() <= max) ){
                vehicle.display();
                isFound = true;
            }
        }
        if(!isFound){
            System.out.println("No results in the Database");
        }

    }

    public void getVehiclesByMakeModel(String make, String model){                                  //for loop and print out vehicles that have that exact make and model inputted
        boolean isFound = false;
        for (Vehicle vehicle : inventory){
            if((vehicle.getMake().equalsIgnoreCase(make)) && (vehicle.getModel().equalsIgnoreCase(model))){
                vehicle.display();
                isFound = true;
            }
        }
        System.out.println();
        if(!isFound){
            System.out.println("No results in the Database");
        }
    }

    public  void getVehiclesByYear(int min, int max){                                           //for loop and print out vehicles that are within the year range
        boolean isFound = false;
        for (Vehicle vehicle : inventory){
            if( (vehicle.getYear() >= min) && (vehicle.getYear() <= max) ){
                vehicle.display();
                isFound = true;
            }
        }
        if(!isFound){
            System.out.println("No results in the Database");
        }

    }

    public void getVehiclesByColor(String color){                                           //for loop and print out vehicles that have that color
        boolean isFound = false;
        for (Vehicle vehicle : inventory){
            if(vehicle.getColor().equalsIgnoreCase(color)) {
                vehicle.display();
                isFound = true;
            }
        }
        System.out.println();
        if(!isFound){
            System.out.println("No results in the Database");
        }
    }
    public void getVehiclesByMileage(int min, int max){                                     //for loop and print out vehicles that are within that mileage range
        boolean isFound = false;
        for (Vehicle vehicle : inventory){
            if( (vehicle.getOdometer() >= min) && (vehicle.getOdometer() <= max) ){
                vehicle.display();
                isFound = true;
            }
        }
        if(!isFound){
            System.out.println("No results in the Database");
        }
    }

    public void getVehiclesByType(String vehicleType){                                  //for loop and print out vehicles that are that vehicle type
        boolean isFound = false;
        for (Vehicle vehicle : inventory){
            if(vehicle.getVehicleType().equalsIgnoreCase(vehicleType)) {
                vehicle.display();
                isFound = true;
            }
        }
        if(!isFound){
            System.out.println("No results in the Database");
        }
    }

    public void getAllVehicles(){                                                       //print all vehicles
        for (Vehicle vehicle : inventory){
           vehicle.display();
        }
    }

    public void vehicleInStock(int vin){
        boolean isFound = false;
        int vehiclePosition = 0;
        for (Vehicle vehicle1 : inventory) {
            if (vehicle1.getVin() == vin) {
                isFound = true;
            }
        }
        if(isFound) {
            System.out.println("Vehicle was found in database.");                       //removes the vehicle at the found position of the arrayList
        }
        else{
            System.out.println("No vehicle found in database. please try again");
        }

    }


    public void addVehicles(Vehicle vehicle){                                       //adds to List
        inventory.add(vehicle);
    }

    public void removeVehicles(int vin) {                                   //goes through list and finds vehicles with the exact same VIN and gets the position of it in the arrayList
        boolean isFound = false;
        int vehiclePosition = 0;
        for (Vehicle vehicle1 : inventory) {
            if (vehicle1.getVin() == vin) {
                vehiclePosition = inventory.indexOf(vehicle1);
                isFound = true;
            }
        }
        if(isFound) {
            inventory.remove(vehiclePosition);                                      //removes the vehicle at the found position of the arrayList
        }
        else{
            System.out.println("No vehicle found in database. please try again");
        }
    }

    public void fileSave(){                                                     //using the parameters save the dealership and List to a .csv file
        DealershipFileManager.saveDealerShipAndListData(this.name, this.address, this.phone, inventory);

    }

}
