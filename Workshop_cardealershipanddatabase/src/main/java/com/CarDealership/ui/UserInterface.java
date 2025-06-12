package com.CarDealership.ui;

import com.CarDealership.contract.LeaseContract;
import com.CarDealership.contract.SalesContract;
import com.CarDealership.data.Dealership;
import com.CarDealership.data.DealershipFileManager;
import com.CarDealership.model.Vehicle;
import com.CarDealership.model.vehicleCreator;

import java.util.Scanner;

public class UserInterface {

    private Dealership dealership;                  //init method will fill in the rest later


    private void init(){
        dealership = DealershipFileManager.getDealership();
    }


    public void display(){
        init();
        DisplayMenu.displayMenu();
        Scanner scanner = new Scanner(System.in);

        int userInput = scanner.nextInt();
        scanner.nextLine();

        switch (userInput){                                                                                             //switch case with multiple options 1 - 6 display cars if they fill the user's input
            case 1:
                System.out.println("Enter Minimum Price, followed by Maximum Price.\n");
                dealership.getVehiclesByPrice(InputPrompter.getDoubleInput(),InputPrompter.getDoubleInput());
                display();
                break;
            case 2:
                System.out.println("Enter Make, followed by Model.\n");
                dealership.getVehiclesByMakeModel(InputPrompter.getSingleString(),InputPrompter.getSingleString());
                display();
                break;
            case 3:
                System.out.println("Enter Minimum Year, followed by Maximum Year.\n");
                dealership.getVehiclesByYear(InputPrompter.getIntInput(),InputPrompter.getIntInput());
                display();
                break;
            case 4:
                System.out.println("Enter Color.\n");
                dealership.getVehiclesByColor(InputPrompter.getSingleString());
                display();
                break;
            case 5:
                System.out.println("Enter Minimum Mileage, followed by Maximum Mileage.\n");
                dealership.getVehiclesByMileage(InputPrompter.getIntInput(),InputPrompter.getIntInput());
                display();
                break;
            case 6:
                System.out.println("Enter Vehicle Type.\n");
                dealership.getVehiclesByType(InputPrompter.getSingleString());
                display();
                break;
            case 7:
                dealership.getAllVehicles();                                                //displays all vehicles
                display();
                break;
            case 8:
                Vehicle addVehicle = vehicleCreator.createVehicle();                        //adds a vehicle to the list and to the .csv file
                dealership.addVehicles(addVehicle);
                dealership.fileSave();
                display();
                break;
            case 9:
                System.out.println("To remove a Vehicle, Please enter its VIN.");
                int vin = InputPrompter.getIntInput();                                                                        //removes a vehicle from the list and from the .csv file
                dealership.removeVehicles(vin);
                dealership.fileSave();
                display();
                break;
            case 10:
                String userChoice = ContractPrompter.saleOrLease();
                if(userChoice.equalsIgnoreCase("s")) {
                    SalesContract salesContract = com.CarDealership.ui.ContractMediator.displayAndReturnSaleContract();
                    dealership.removeVehicles(salesContract.getVehicle().getVin());
                    dealership.fileSave();
                }
                else if(userChoice.equalsIgnoreCase("l")){
                    LeaseContract leaseContract = ContractMediator.displayAndReturnLeaseContract();
                    dealership.removeVehicles(leaseContract.getVehicle().getVin());
                    dealership.fileSave();
                }

                display();
                break;
            case 99:                                                                        //exit method
                exit();
                break;
            default:
                System.out.println("Entry was not one of the following options, Please Try Again");         //if any other option is inputted it will prompt the user to put an appropriate response
                display();
        }

    }
    private void exit(){                                                                //exit command
        System.out.println("Exiting the program, have a good day.");
        System.exit(0);
    }
}
