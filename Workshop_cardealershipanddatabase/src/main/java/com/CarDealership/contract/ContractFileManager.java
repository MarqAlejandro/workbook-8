package com.CarDealership.contract;

import com.CarDealership.model.Vehicle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ContractFileManager {

    private static File file = new File("src/main/resources/Contracts.csv");

    private static List<Contract> contractList = getContractList();

    public static List<Contract> getContractList(){

        List<Contract> contractList = new ArrayList<>();

        try{
            BufferedReader bufReader = new BufferedReader(new FileReader(file));     //BufferedReader variable that takes a FileReader as arguement that takes a .csv file arguement
            String FileInput;

          //  bufReader.readLine();

            while ((FileInput = bufReader.readLine()) != null) {                                            //in the midst of while loop read a line from .csv file and load it onto String Variable and check if it comes out null


                String[] tokens = FileInput.split(Pattern.quote("|"));                                   //load the line onto a String array so that it can be partitioned by the pattern "|"
                SalesContract salesContract = new SalesContract();
                LeaseContract leaseContract = new LeaseContract();

                if (tokens.length == 18) {
                    if (tokens[0].equalsIgnoreCase("sale")) {
                        salesContract.setDate(tokens[1]);
                        salesContract.setName(tokens[2]);
                        salesContract.setEmail(tokens[3]);
                        if (tokens[15].equalsIgnoreCase("yes")) {
                            salesContract.setFinancing(true);
                        } else {
                            salesContract.setFinancing(false);
                        }

                        Vehicle vehicle = new Vehicle();
                        vehicle.setVin(Integer.parseInt(tokens[4]));
                        vehicle.setYear(Integer.parseInt(tokens[5]));
                        vehicle.setMake(tokens[6]);
                        vehicle.setModel(tokens[7]);
                        vehicle.setVehicleType(tokens[8]);
                        vehicle.setColor(tokens[9]);
                        vehicle.setOdometer(Integer.parseInt(tokens[10]));
                        vehicle.setPrice(Double.parseDouble(tokens[11]));
                        salesContract.setVehicle(vehicle);
                        //sales tax = tokens[12];
                        //recording fee = tokens[13];
                        //processing fee = tokens[14];
                        //monthly payment = tokens[16];

                        contractList.add(salesContract);
                    }
                }
                else if(tokens.length == 16){
                    if(tokens[0].equalsIgnoreCase("lease")){
                        leaseContract.setDate(tokens[1]);
                        leaseContract.setName(tokens[2]);
                        leaseContract.setEmail(tokens[3]);

                        Vehicle vehicle = new Vehicle();
                        vehicle.setVin(Integer.parseInt(tokens[4]));
                        vehicle.setYear(Integer.parseInt(tokens[5]));
                        vehicle.setMake(tokens[6]);
                        vehicle.setModel(tokens[7]);
                        vehicle.setVehicleType(tokens[8]);
                        vehicle.setColor(tokens[9]);
                        vehicle.setOdometer(Integer.parseInt(tokens[10]));
                        vehicle.setPrice(Double.parseDouble(tokens[11]));
                        leaseContract.setVehicle(vehicle);

                        contractList.add(leaseContract);
                    }

                }
                else {
                    System.out.println("error: missing or too much information on a given Contract");
                }

            }
        }
        catch (IOException e) {
            System.out.println("error with .csv file naming or pathing, please check if its the correct save file");
        }

        return contractList;
    }

    public static void saveSaleContractData(SalesContract saleContract){
        String delimiter = "|";
        String nLine = "\n";
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, true);

            fileWriter.write("\nSALE" + delimiter +
                    saleContract.getDate() + delimiter +
                    saleContract.getName() + delimiter +
                    saleContract.getEmail() + delimiter +
                    saleContract.vehicle.getVin() + delimiter +
                    saleContract.vehicle.getYear() + delimiter +
                    saleContract.vehicle.getMake() + delimiter +
                    saleContract.vehicle.getModel() + delimiter +
                    saleContract.vehicle.getVehicleType() + delimiter +
                    saleContract.vehicle.getColor() + delimiter +
                    saleContract.vehicle.getOdometer() + delimiter +
                    saleContract.vehicle.getPrice() + delimiter +
                    (int) saleContract.getJustSalesTax(saleContract.vehicle.getPrice()) + delimiter +
                    saleContract.getRECORDING_FEE() + delimiter);
            if(saleContract.vehicle.getPrice() >= 10_000){
                fileWriter.write(saleContract.getPROCESSING_FEE_O10K() + delimiter);
            }
            else{
                fileWriter.write(saleContract.getPROCESSING_FEE_U10K() + delimiter);
            }
            fileWriter.write((saleContract.getTotalPrice() + delimiter));
            if(saleContract.isFinancing()){
                fileWriter.write("YES" + delimiter);
            }
            else {
                fileWriter.write("NO" + delimiter);
            }
            fileWriter.write(String.valueOf((int) saleContract.getMonthlyPayment()));

            fileWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //build similar method for leases

    public static void saveLeaseContractData(LeaseContract leaseContract){
        String delimiter = "|";
        String nLine = "\n";
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, true);

            fileWriter.write("\nLEASE" + delimiter +
                    leaseContract.getDate() + delimiter +
                    leaseContract.getName() + delimiter +
                    leaseContract.getEmail() + delimiter +
                    leaseContract.vehicle.getVin() + delimiter +
                    leaseContract.vehicle.getYear() + delimiter +
                    leaseContract.vehicle.getMake() + delimiter +
                    leaseContract.vehicle.getModel() + delimiter +
                    leaseContract.vehicle.getVehicleType() + delimiter +
                    leaseContract.vehicle.getColor() + delimiter +
                    leaseContract.vehicle.getOdometer() + delimiter +
                    leaseContract.vehicle.getPrice() + delimiter +


                    leaseContract.getExpectedEndingValue(leaseContract.vehicle.getPrice()) + delimiter +
                    (int) leaseContract.getLeaseFee(leaseContract.vehicle.getPrice()) + delimiter +
                    leaseContract.getTotalPrice() + delimiter +
                    (int) leaseContract.getMonthlyPayment());

            fileWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void fileExist(){

        if(file.exists()){
            System.out.println("file exists");

        }
        else{
            System.out.println("the file simply doesn't exist");
        }
    }


}
