package com.CarDealership.model;

import java.util.InputMismatchException;
import java.util.Scanner;

public class vehicleCreator {

    public static Vehicle createVehicle(){                          //prompts the user for vehicle information, returns a new vehicle object
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter VIN: ");
            int vin = scanner.nextInt();

            System.out.print("\nEnter Year: ");
            int year = scanner.nextInt();
            scanner.nextLine();

            System.out.print("\nEnter make: ");
            String make = scanner.nextLine();

            System.out.print("\nEnter model: ");
            String model = scanner.nextLine();

            System.out.print("\nEnter Vehicle Type: ");
            String vehicleType = scanner.nextLine();

            System.out.print("\nEnter Color: ");
            String color = scanner.nextLine();

            System.out.print("\nEnter Odometer reading: ");
            int odometer = scanner.nextInt();

            System.out.print("\nEnter Price: ");
            double price = scanner.nextDouble();

            scanner.nextLine();

            return new Vehicle(vin,year,make,model,vehicleType,color,odometer,price);


        }
        catch (InputMismatchException e){
            System.out.println("1 or more inputs were incorrect. Please Try Again.");                   //if 1 or more inputs are of the wrong data type then it will loop back to the beginning
            createVehicle();
        }


        return new Vehicle();
    }

    public static Vehicle createVehicleID(){                          //prompts the user for vehicle information, returns a new vehicle object
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter VIN: ");
            int vin = scanner.nextInt();

            scanner.nextLine();

            System.out.print("\nEnter Price: ");
            double price = scanner.nextDouble();

            scanner.nextLine();

            return new Vehicle(vin, price);


        }
        catch (InputMismatchException e){
            System.out.println("1 or more inputs were incorrect. Please Try Again.");                   //if 1 or more inputs are of the wrong data type then it will loop back to the beginning
            createVehicleID();
        }


        return new Vehicle();
    }


}
