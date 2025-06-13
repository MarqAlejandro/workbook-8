package com.CarDealership.data;

import com.CarDealership.data.dealership.Dealership;
import com.CarDealership.model.Vehicle;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VehicleDatabaseManager {

    public static void addVehicleToTableVehicles(Vehicle vehicle) {
        String query = "INSERT INTO MARQ.vehicles(vin, year, make, model, vehicle_type, color, odometer, price)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?);\n " +
                "INSERT INTO MARQ.inventory(dealership_id,vin)\n" +
                "VALUES(?, ?);";

        try (Connection conn = DriverManager.getConnection(DatabaseDriver.getUrl());
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, vehicle.getVin());
            stmt.setInt(2, vehicle.getYear());
            stmt.setString(3, vehicle.getMake());
            stmt.setString(4, vehicle.getModel());
            stmt.setString(5, vehicle.getVehicleType());
            stmt.setString(6, vehicle.getColor());
            stmt.setInt(7, vehicle.getOdometer());
            stmt.setBigDecimal(8, BigDecimal.valueOf(vehicle.getPrice()));



            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("a row was inserted successfully!");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addVehicleToInventory(Dealership dealership, Vehicle vehicle){
        String query ="INSERT INTO MARQ.inventory(dealership_id,vin)\n" +
                "VALUES(?, ?)";


        try (Connection conn = DriverManager.getConnection(DatabaseDriver.getUrl());
             PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, dealership.getDealershipID());
        stmt.setInt(2, vehicle.getVin());

        int rowsInserted = stmt.executeUpdate();

        if (rowsInserted > 0) {
            System.out.println("a row was inserted successfully!");
        }


        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
