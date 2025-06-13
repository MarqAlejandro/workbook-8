package com.CarDealership.data;

import com.CarDealership.data.dealership.Dealership;
import com.CarDealership.model.Vehicle;
import com.CarDealership.ui.InputPrompter;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DealershipDatabaseManager {

    public static List<Vehicle> getDatabaseInventory(String name){
        List<Vehicle> inventoryOfVehicles = new ArrayList<>();

        String query = "SELECT * FROM MARQ.INVENTORY AS i LEFT JOIN MARQ.DEALERSHIPS AS d\n" +
                "  ON i.DEALERSHIP_ID = d.DEALERSHIP_ID LEFT JOIN MARQ.VEHICLES AS v\n" +
                "  ON i.VIN = v.VIN \n" +
                " WHERE d.NAME LIKE ?;";

        try( Connection conn = DriverManager.getConnection(DatabaseDriver.getUrl());
             PreparedStatement stmt = conn.prepareStatement(query) ) {

            stmt.setString(1,name);
            ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Vehicle vehicle = new Vehicle();

                    vehicle.setVin(rs.getInt("vin"));
                    vehicle.setYear(rs.getInt("year"));
                    vehicle.setMake(rs.getString("make"));
                    vehicle.setModel(rs.getString("model"));
                    vehicle.setVehicleType(rs.getString("vehicle_type"));
                    vehicle.setColor(rs.getString("color"));
                    vehicle.setOdometer(rs.getInt("odometer"));
                    vehicle.setPrice(rs.getBigDecimal("price").doubleValue());

                    inventoryOfVehicles.add(vehicle);
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return inventoryOfVehicles;
    }

    public static Dealership getDatabaseDealershipInfo(){
        Dealership dealership = new Dealership();

        System.out.println("Enter Dealership Name");
        String dealershipName = InputPrompter.getSingleString();
        String query = "SELECT * FROM MARQ.DEALERSHIPS WHERE name LIKE ? ";

        try( Connection conn = DriverManager.getConnection(DatabaseDriver.getUrl());
             PreparedStatement stmt = conn.prepareStatement(query) ) {

            stmt.setString(1, "%" + dealershipName + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                dealership.setDealershipID(rs.getInt("dealership_id"));
                dealership.setName(rs.getString("name"));
                dealership.setAddress(rs.getString("address"));
                dealership.setPhone(rs.getString("phone"));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new Dealership(dealership.getDealershipID(), dealership.getName(),dealership.getAddress(),dealership.getPhone());
    }

}
