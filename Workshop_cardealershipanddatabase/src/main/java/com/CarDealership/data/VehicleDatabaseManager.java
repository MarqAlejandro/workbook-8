package com.CarDealership.data;

import com.CarDealership.data.dealership.Dealership;
import com.CarDealership.model.Vehicle;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VehicleDatabaseManager {

    public static void addVehicleToTables(Dealership dealership, Vehicle vehicle) {
        String vehicleQuery = "INSERT INTO MARQ.vehicles(vin, year, make, model, vehicle_type, color, odometer, price)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?);\n ";
        String inventoryQuery = "INSERT INTO MARQ.inventory(dealership_id,vin)\n" +
                "VALUES(?, ?);";

        try (Connection conn = DriverManager.getConnection(DatabaseDriver.getUrl())) {
            try (PreparedStatement vehicleStmt = conn.prepareStatement(vehicleQuery)) {

                vehicleStmt.setInt(1, vehicle.getVin());
                vehicleStmt.setInt(2, vehicle.getYear());
                vehicleStmt.setString(3, vehicle.getMake());
                vehicleStmt.setString(4, vehicle.getModel());
                vehicleStmt.setString(5, vehicle.getVehicleType());
                vehicleStmt.setString(6, vehicle.getColor());
                vehicleStmt.setInt(7, vehicle.getOdometer());
                vehicleStmt.setBigDecimal(8, BigDecimal.valueOf(vehicle.getPrice()));


                int vehicleRowsInserted = vehicleStmt.executeUpdate();

                try (PreparedStatement inventoryStmt = conn.prepareStatement(inventoryQuery)) {
                    inventoryStmt.setInt(1, dealership.getDealershipID());
                    inventoryStmt.setInt(2, vehicle.getVin());

                    int inventoryRows = inventoryStmt.executeUpdate();

                    int rowsInserted = inventoryRows + vehicleRowsInserted;

                    if (rowsInserted > 0) {
                        System.out.printf("%d row was inserted successfully!", rowsInserted);
                    }


                }

            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void removeVehicleFromTables(int vin){
        String vehicleQuery = "DELETE FROM MARQ.vehicles\n" +
                " WHERE vin = ?\n ";
        String inventoryQuery = "DELETE FROM MARQ.inventory\n" +
                " WHERE vin = ?;";

        try (Connection conn = DriverManager.getConnection(DatabaseDriver.getUrl())) {
            try (PreparedStatement vehicleStmt = conn.prepareStatement(vehicleQuery)) {

                vehicleStmt.setInt(1, vin);

                int vehicleRowsInserted = vehicleStmt.executeUpdate();

                try (PreparedStatement inventoryStmt = conn.prepareStatement(inventoryQuery)) {

                    inventoryStmt.setInt(1, vin);

                    int inventoryRows = inventoryStmt.executeUpdate();

                    int rowsInserted = inventoryRows + vehicleRowsInserted;

                    if (rowsInserted > 0) {
                        System.out.printf("%d row was inserted successfully!", rowsInserted);
                    }


                }

            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }





    }

}
