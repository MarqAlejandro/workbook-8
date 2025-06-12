package com.CarDealership.ui;

public class DisplayMenu {

    public static void displayMenu(){                       //menu displaying class

        System.out.print("""
                
                Reports Display Screen, please input one of the following: \
                
                \t1) - Search by Price Range\
                
                \t2) - Search by make/model\
                
                \t3) - Search by Year\
                
                \t4) - Search by color\
                
                \t5) - Search by Mileage\
                
                \t6) - Search by Type\
                
                \t7) - List All Vehicles\
                
                \t8) - Add a Vehicle\
                
                \t9) - Remove a Vehicle\
                
                \t10) - Sale / Lease a Vehicle\
                
                \t99) - Quit
                
                Enter:\s""");
    }
}
