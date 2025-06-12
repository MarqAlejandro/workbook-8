package com.CarDealership.data;

import com.CarDealership.model.Vehicle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DealershipFileManager {

   private static File file = new File("src/main/resources/Inventory.csv");                 //pre-determined path can be Copy/Pasted by right-clicking on intellij's project file list

   public static List<Vehicle> getInventory(){

       List<Vehicle> inventoryOfVehicles = new ArrayList<>();

       try {

           BufferedReader bufReader = new BufferedReader(new FileReader(file));     //BufferedReader variable that takes a FileReader as arguement that takes a .csv file arguement
           String FileInput;

           bufReader.readLine();                                                                           //skip the first line, assumes that the first line is headers and garbage data

           while ((FileInput = bufReader.readLine()) != null) {                                            //in the midst of while loop read a line from .csv file and load it onto String Variable and check if it comes out null
               String[] tokens = FileInput.split(Pattern.quote("|"));                                   //load the line onto a String array so that it can be partitioned by the pattern "|"
               Vehicle vehicle = new Vehicle();             //create an empty Vehicle object
                if (tokens.length == 8) {                                                                  //will only load the vehicle's infomation if there is
                   vehicle.setVin(Integer.parseInt(tokens[0]));

                   vehicle.setYear(Integer.parseInt(tokens[1]));

                   vehicle.setMake(tokens[2]);

                   vehicle.setModel(tokens[3]);

                   vehicle.setVehicleType(tokens[4]);

                   vehicle.setColor(tokens[5]);

                   vehicle.setOdometer(Integer.parseInt(tokens[6]));

                   vehicle.setPrice(Double.parseDouble(tokens[7]));
               } else {
                   System.out.println("error: missing or too much information on a given transaction");
               }
               inventoryOfVehicles.add(vehicle);                                                         //load the object onto the ArrayList for Transaction objects
           }
           bufReader.close();

       } catch (IOException e) {                                                                       //in case of an error with I/O, however should never proc
           System.out.println("error with .csv file naming or pathing, please check if its the correct save file");

       }

       return inventoryOfVehicles;
   }

   public static Dealership getDealership(){                                                            //reads the .csv file and reads only the 1st line to get the Dealership's information
       Dealership dealership = new Dealership();
       BufferedReader bufReader = null;
       try {
           bufReader = new BufferedReader(new FileReader(file));
           String FileInput = bufReader.readLine();
           String[] tokens = FileInput.split(Pattern.quote("|"));
           if(tokens.length == 3) {
               dealership.setName(tokens[0]);
               dealership.setAddress(tokens[1]);
               dealership.setPhone(tokens[2]);
           }
           else{
               System.out.println("missing or too much info for a dealership");
           }
       } catch (IOException e) {
           System.out.println("file not found");
       }
       return dealership;
   }

   // this method overwrites to the Inventory.csv file

   public static void saveDealerShipAndListData(String name, String address, String phone, List<Vehicle> inventory){            //saves the data to a .csv file via FileWriter
       try {
           String delimiter = "|";
           String nLine = "\n";
           FileWriter fileWriter = new FileWriter("src/main/resources/Inventory.csv");

           fileWriter.write(name + delimiter +
                   address + delimiter + phone + nLine);

           for (Vehicle vehicle : inventory){
               fileWriter.write(
                     vehicle.getVin() + delimiter
                       + vehicle.getYear() + delimiter
                       + vehicle.getMake() + delimiter
                       + vehicle.getModel() + delimiter
                       + vehicle.getVehicleType() + delimiter
                       + vehicle.getColor() + delimiter
                       + vehicle.getOdometer() + delimiter
                       + vehicle.getPrice() + nLine );
           }

           fileWriter.close();


       } catch (IOException e) {
           System.out.println("File was not found, please make sure the pathing is correct");
       }

   }


}
