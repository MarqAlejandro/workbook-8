package com.CarDealership.data;

import com.microsoft.sqlserver.jdbc.ISQLServerDataSource;

import java.sql.*;

public class DatabaseDriver {


        public static void main(String[] args) {

            String url = "jdbc:sqlserver://skills4it.database.windows.net:1433;" +
                    "database=Courses;" +
                    "user=user419@skills4it;" +
                    "password=YearupSecure2025!;" +
                    "encrypt=true;" +
                    "trustServerCertificate=false;" +
                    "loginTimeout=30;";



            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM MARQ.DEALERSHIPS")) {

                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("dealership_id") +
                            ", Name: " + rs.getString("name"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }



        }



}