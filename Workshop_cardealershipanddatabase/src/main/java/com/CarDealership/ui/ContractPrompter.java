package com.CarDealership.ui;

import com.CarDealership.contract.LeaseContract;
import com.CarDealership.contract.SalesContract;
import com.CarDealership.model.Vehicle;
import com.CarDealership.model.vehicleCreator;

import java.util.Scanner;

public class ContractPrompter {

    public static String saleOrLease(){
        System.out.println("For a Sale, press \"S\"\tFor a Lease, press \"L\"");

        return InputPrompter.getSingleString();
    }

    public static SalesContract getSaleContract(){
        System.out.println("Enter Today's Date (Formatted as mm/dd/yyyy).");
        String date = InputPrompter.getSingleString();
        System.out.println("Enter Name.");
        String name = InputPrompter.getSingleString();
        System.out.println("Enter E-Mail.");
        String email = InputPrompter.getSingleString();

        System.out.println("Enter the following information for the car being sold");
        Vehicle vehicle = vehicleCreator.createVehicle();

        System.out.println("Vehicle Price: " + vehicle.getPrice() + " + additional fees and taxes.\n" +
                "Would you like to finance the car?");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        if(userInput.equalsIgnoreCase("yes")){
            return new SalesContract(date, name, email, vehicle, true);
        }
        else if(userInput.equalsIgnoreCase("no")){
            return new SalesContract(date, name, email, vehicle, false);
        }
        else{
            System.out.println("User's input was neither a yes or no. Defaulting to NO option");
            return new SalesContract(date, name, email, vehicle, false);
        }

    }

    public static LeaseContract getLeaseContract(){
        System.out.println("Enter Today's Date (Formatted as mm/dd/yyyy).");
        String date = InputPrompter.getSingleString();
        System.out.println("Enter Name.");
        String name = InputPrompter.getSingleString();
        System.out.println("Enter E-Mail.");
        String email = InputPrompter.getSingleString();

        System.out.println("Enter the following information for the car being sold");
        Vehicle vehicle = vehicleCreator.createVehicle();

        return new LeaseContract(date,name,email,vehicle);
    }


}
