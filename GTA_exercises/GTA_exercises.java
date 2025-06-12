package com.plurasight;  // Define the namespace for this class, grouping related classes together

import java.sql.*;        // Import JDBC classes for database connectivity
import java.util.Scanner;  // Import Scanner to read user input from the console

public class Stuff {       // Main public class containing all methods for the CLI

    // Connection details for SQL Server (JDBC URL)
    private static final String DB_URL =
        "jdbc:sqlserver://skills4it.database.windows.net:1433;" +  // Server address and port
        "database=Courses;" +                                    // Target database name
        "user=gtareader@skills4it;" +                            // Username for authentication
        "password=StrongPass!2025;" +                           // Password (keep this secure!)
        "encrypt=true;" +                                        // Encrypt connection
        "trustServerCertificate=false;" +                        // Do not trust self-signed certs
        "loginTimeout=30;";                                      // Wait time before timeout

    public static void main(String[] args) {
        // Entry point: start here when the program runs
        Scanner inputScanner = new Scanner(System.in);
        // Scanner reads integers from System.in for menu selection

        while (true) {  // Infinite loop: keeps showing menu until user exits
            System.out.println("\n=== Bay City SQL CLI ===");  // Print blank line + header
            System.out.println("1. Suspect Scanner (WHERE)");
            System.out.println("2. Vehicle Watchlist (JOIN + WHERE)");
            System.out.println("3. Reward Tracker (GROUP BY + SUM + ORDER BY)");
            System.out.println("4. Elite Agent Filter (GROUP BY + HAVING)");
            System.out.println("5. Search Person");
            System.out.println("6. Search Vehicle");
            System.out.println("7. Search Vehicles A Person Owns");
            System.out.println("8. Find AVG Mission Payout");
            System.out.println("9. Find Inactive Agents");
            System.out.println("10. Find Top Earning Criminals");
            System.out.println("0. Exit");
            System.out.print("Select mission: ");  // Prompt without newline

            int choice = inputScanner.nextInt();  // Read user's menu choice

            // Decide which method to call based on user input
            switch (choice) {
                case 1 -> runSuspectScanner();           // Call method 1
                case 2 -> runVehicleWatchlist();         // Call method 2
                case 3 -> runRewardTracker();            // Call method 3
                case 4 -> runEliteAgentFilter();         // Call method 4
                case 5 -> searchPerson();                // Call method 5
                case 6 -> searchVehicle();               // Call method 6
                case 7 -> searchAPersonVehicles();       // Call method 7
                case 8 -> runAvgPayout();                // Call method 8
                case 9 -> runInactiveAgentReport();      // Call method 9
                case 10 -> runHighestEarningCriminals(); // Call method 10
                case 0 -> System.exit(0);                // Exit program
                default -> System.out.println("Invalid choice."); // Handle bad input
            }
        }
    }

    // ----------------------------
    // Method: runSuspectScanner
    // Purpose: Find suspects with WantedLevel >= 2
    public static void runSuspectScanner() {
        // Define SQL query with placeholder '?' for dynamic parameter
        String query = "SELECT Name, Alias, WantedLevel FROM GTA.Citizens WHERE WantedLevel >= ? ";

        try (
            Connection conn = DriverManager.getConnection(DB_URL);            // Open DB connection
            PreparedStatement stmt = conn.prepareStatement(query)           // Prepare SQL statement
        ) {
            stmt.setInt(1, 2);         // Fill in placeholder: minimum WantedLevel = 2
            ResultSet rs = stmt.executeQuery();  // Run query and get results

            System.out.println("\n--- Suspects with WantedLevel >= 2 ---");
            while (rs.next()) {         // Loop through each row in the ResultSet
                // Print formatted columns: %-20s = left-aligned text width 20
                System.out.printf(
                    "%-20s %-20s Wanted Level: %d\n",
                    rs.getString("Name"),        // Retrieve 'Name' column
                    rs.getString("Alias"),       // Retrieve 'Alias' column
                    rs.getInt("WantedLevel")     // Retrieve 'WantedLevel'
                );
            }
        } catch (SQLException e) {     // Catch any SQL errors
            System.err.println("Error: " + e.getMessage()); // Print error message
        }
    }

    // ----------------------------
    // Method: runVehicleWatchlist
    // Purpose: List stolen vehicles and their owners
    public static void runVehicleWatchlist() {
        // Join Citizens and Vehicles tables to find stolen vehicles
        String query =
            "SELECT c.Name, v.Type, v.Brand " +
            "FROM GTA.Citizens c JOIN GTA.Vehicles v " +
            "ON c.CitizenID = v.OwnerID " +
            "WHERE v.IsStolen = 1";         // Only stolen = 1

        try (
            Connection conn = DriverManager.getConnection(DB_URL);            // Connect to DB
            PreparedStatement stmt = conn.prepareStatement(query);           // Prepare statement
            ResultSet rs = stmt.executeQuery()                               // Execute and fetch results
        ) {
            System.out.println("\n--- Stolen Vehicles and Their Owners ---");
            while (rs.next()) {         // Iterate through results
                System.out.printf(
                    "%-20s %-15s %-15s\n",
                    rs.getString("Name"), // Owner's name
                    rs.getString("Type"), // Vehicle type
                    rs.getString("Brand") // Vehicle brand
                );
            }
        } catch (SQLException e) {     // Handle exceptions
            System.err.println("Error: " + e.getMessage()); // Print error
        }
    }

    // ----------------------------
    // Method: runRewardTracker
    // Purpose: Show total earnings per citizen from missions
    public static void runRewardTracker() {
        // SQL uses GROUP BY to sum rewards per citizen, then ORDER BY desc
        String query =
            "SELECT C.Name, SUM(M.Reward) AS TotalEarnings\n" +
            "FROM GTA.Citizens C\n" +
            "JOIN GTA.Assignments A ON C.CitizenID = A.CitizenID\n" +
            "JOIN GTA.Missions M ON M.MissionID = A.MissionID\n" +
            "GROUP BY C.Name\n" +
            "ORDER BY TotalEarnings DESC;";

        try (
            Connection conn = DriverManager.getConnection(DB_URL);            // Open connection
            PreparedStatement stmt = conn.prepareStatement(query);           // Prepare
            ResultSet rs = stmt.executeQuery()                               // Execute
        ) {
            System.out.println("\n--- Reward Tracker ---");
            while (rs.next()) {         // Loop through each result row
                System.out.printf(
                    "%-20s $%.2f\n",
                    rs.getString("Name"),          // Citizen name
                    rs.getDouble("TotalEarnings")  // Total rewards sum
                );
            }
        } catch (SQLException e) {     // SQL exception handling
            System.err.println("Error: " + e.getMessage());
        }
    }

        // ----------------------------
    // Method: runEliteAgentFilter
    // Purpose: Identify elite agents with more than a specified number of missions
    public static void runEliteAgentFilter() {
        // Build SQL: count missions per agent, filter using HAVING
        String query = 
            "SELECT AgentID, COUNT(*) AS MissionCount " +  // Agent ID and count
            "FROM GTA.Assignments " +                     // Table of mission assignments
            "GROUP BY AgentID " +                        // Group rows by agent
            "HAVING COUNT(*) > 5;";                        // Only agents with >5 missions

        try (
            Connection conn = DriverManager.getConnection(DB_URL);   // Connect using DB_URL
            PreparedStatement stmt = conn.prepareStatement(query);   // Prepare the grouped query
            ResultSet rs = stmt.executeQuery()                      // Execute and get results
        ) {
            System.out.println("--- Elite Agents with > 5 Missions ---");
            while (rs.next()) {                // For each row returned
                int agentId = rs.getInt("AgentID");           // Read AgentID column
                int missionCount = rs.getInt("MissionCount"); // Read count column
                // Print agent and mission count
                System.out.printf("Agent %d - Missions: %d ", agentId, missionCount);
            }
        } catch (SQLException e) {            // Handle SQL errors
            System.err.println("Error: " + e.getMessage()); // Print error details
        }
    }

    // ----------------------------
    // Method: searchPerson
    // Purpose: Prompt for a name substring and search in Citizens table
    public static void searchPerson() {
        Scanner scanner = new Scanner(System.in);              // New Scanner for user input
        System.out.print("Enter person name to search: ");    // Prompt user
        String name = scanner.nextLine();                      // Read full line input

        // Use LIKE with wildcards for flexible matching
        String query = "SELECT * FROM GTA.Citizens WHERE Name LIKE ?";
        try (
            Connection conn = DriverManager.getConnection(DB_URL);       // Open DB connection
            PreparedStatement stmt = conn.prepareStatement(query)        // Prepare statement with placeholder
        ) {
            stmt.setString(1, "%" + name + "%");                    // Surround input with % for partial matches
            ResultSet rs = stmt.executeQuery();                         // Execute the query

            System.out.println(" --- Search Results for '" + name + "' ---");
            while (rs.next()) {                                          // Iterate results
                int id = rs.getInt("CitizenID");                      // Citizen ID column
                String fullName = rs.getString("Name");               // Name column
                String alias = rs.getString("Alias");                 // Alias column
                int level = rs.getInt("WantedLevel");                // Wanted level column
                // Print formatted details per person
                System.out.printf(
                    "ID: %d, Name: %s, Alias: %s, WantedLevel: %d ",
                    id, fullName, alias, level
                );
            }
        } catch (SQLException e) {                                     // SQL errors
            System.err.println("Error: " + e.getMessage());          // Print error
        }
    }

    // ----------------------------
    // Method: searchVehicle
    // Purpose: Prompt for brand or type and find matching vehicles
    public static void searchVehicle() {
        Scanner scanner = new Scanner(System.in);                     // Scanner for input
        System.out.print("Enter vehicle brand or type: ");          // Prompt user
        String input = scanner.nextLine();                            // Read input line

        // Query both Brand and Type with OR condition
        String query = "SELECT * FROM GTA.Vehicles WHERE Brand LIKE ? OR Type LIKE ?";

        try (
            Connection conn = DriverManager.getConnection(DB_URL);       // Connect to DB
            PreparedStatement stmt = conn.prepareStatement(query)        // Prepare statement
        ) {
            stmt.setString(1, "%" + input + "%");                    // First placeholder: Brand
            stmt.setString(2, "%" + input + "%");                    // Second placeholder: Type
            ResultSet rs = stmt.executeQuery();                         // Execute

            System.out.println(" --- Vehicle Search for '" + input + "' ---");
            while (rs.next()) {                                          // Iterate rows
                int vid = rs.getInt("VehicleID");                     // Vehicle ID column
                String brand = rs.getString("Brand");                 // Brand column
                String type = rs.getString("Type");                   // Type column
                boolean stolen = rs.getBoolean("IsStolen");           // Stolen flag
                // Print vehicle details with status
                System.out.printf(
                    "ID: %d, Brand: %s, Type: %s, Stolen: %b ",
                    vid, brand, type, stolen
                );
            }
        } catch (SQLException e) {                                     // Handle exceptions
            System.err.println("Error: " + e.getMessage());          // Display error
        }
    }

    // ----------------------------
    // Method: searchAPersonVehicles
    // Purpose: Given a citizen ID, list all their vehicles
    public static void searchAPersonVehicles() {
        Scanner scanner = new Scanner(System.in);                     // Scanner for ID input
        System.out.print("Enter person ID: ");                      // Prompt for ID
        int id = scanner.nextInt();                                   // Read integer ID

        // Query vehicles where OwnerID matches provided ID
        String query = 
            "SELECT VehicleID, Brand, Type FROM GTA.Vehicles WHERE OwnerID = ?";

        try (
            Connection conn = DriverManager.getConnection(DB_URL);   // DB connection
            PreparedStatement stmt = conn.prepareStatement(query)    // Prepare statement
        ) {
            stmt.setInt(1, id);                                     // Fill in ID placeholder
            ResultSet rs = stmt.executeQuery();                     // Execute

            System.out.println(" --- Vehicles Owned by ID " + id + " ---");
            while (rs.next()) {                                      // Iterate results
                int vid = rs.getInt("VehicleID");                 // VehicleID
                String brand = rs.getString("Brand");             // Brand
                String type = rs.getString("Type");               // Type
                // Print each vehicle line
                System.out.printf(
                    "ID: %d, Brand: %s, Type: %s ",
                    vid, brand, type
                );
            }
        } catch (SQLException e) {                                 // SQL exception
            System.err.println("Error: " + e.getMessage());      // Error message
        }
    }

    // ----------------------------
    // Method: runAvgPayout
    // Purpose: Calculate the average reward amount for all missions
    public static void runAvgPayout() {
        // Simple aggregate to compute average
        String query = "SELECT AVG(Reward) AS AvgPayout FROM GTA.Missions";

        try (
            Connection conn = DriverManager.getConnection(DB_URL);   // Open connection
            PreparedStatement stmt = conn.prepareStatement(query);   // Prepare
            ResultSet rs = stmt.executeQuery()                      // Execute and fetch
        ) {
            if (rs.next()) {                                       // Check if there's a result
                double avg = rs.getDouble("AvgPayout");          // Read AvgPayout column
                System.out.printf("Average Mission Payout: $%.2f ", avg); // Print formatted
            }
        } catch (SQLException e) {                                 // Handle SQL errors
            System.err.println("Error: " + e.getMessage());      // Print error message
        }
    }

    // ----------------------------
    // Method: runInactiveAgentReport
    // Purpose: Find agents with zero assigned missions
    public static void runInactiveAgentReport() {
        // Use NOT EXISTS subquery to find agents without assignments
        String query = 
            "SELECT AgentID FROM GTA.Agents a " +
            "WHERE NOT EXISTS (SELECT 1 FROM GTA.Assignments asn WHERE asn.AgentID = a.AgentID)";

        try (
            Connection conn = DriverManager.getConnection(DB_URL);   // Connect to DB
            PreparedStatement stmt = conn.prepareStatement(query);   // Prepare statement
            ResultSet rs = stmt.executeQuery()                      // Execute
        ) {
            System.out.println(" --- Inactive Agents ---");
            while (rs.next()) {                                     // Iterate rows
                int agentId = rs.getInt("AgentID");              // Read agent ID
                System.out.println("Agent ID: " + agentId);      // Print ID
            }
        } catch (SQLException e) {                                 // Catch SQL exceptions
            System.err.println("Error: " + e.getMessage());      // Display error
        }
    }

    // ----------------------------
    // Method: runHighestEarningCriminals
    // Purpose: Show top criminals by total mission rewards
    public static void runHighestEarningCriminals() {
        // Similar to runRewardTracker but filter for criminals only
        String query =
            "SELECT C.Name, SUM(M.Reward) AS Earnings " +
            "FROM GTA.Citizens C " +
            "JOIN GTA.Assignments A ON C.CitizenID = A.CitizenID " +
            "JOIN GTA.Missions M ON M.MissionID = A.MissionID " +
            "WHERE C.IsCriminal = 1 " +
            "GROUP BY C.Name " +
            "ORDER BY Earnings DESC LIMIT 5;";                   // Top 5 criminals

        try (
            Connection conn = DriverManager.getConnection(DB_URL);   // Connect
            PreparedStatement stmt = conn.prepareStatement(query);   // Prepare
            ResultSet rs = stmt.executeQuery()                      // Execute
        ) {
            System.out.println(" --- Top 5 Earning Criminals ---");
            while (rs.next()) {                                     // Loop results
                String name = rs.getString("Name");              // Criminal name
                double earnings = rs.getDouble("Earnings");      // Total earnings
                // Print criminal earnings
                System.out.printf("%s: $%.2f ", name, earnings);
            }
        } catch (SQLException e) {                                 // SQL errors
            System.err.println("Error: " + e.getMessage());      // Print error
        }
    }
}
