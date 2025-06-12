package com.CarDealership.contract;

import com.CarDealership.model.Vehicle;

public class LeaseContract extends Contract{

    private final double EXPECTED_ENDING_VALUE_PERCENTILE = 0.5;
    private final double LEASE_FEE = 0.07;
    private final double FINANCING_PERCENTILE = 1.04;

    public LeaseContract(String date, String name, String email, Vehicle vehicle) {
        super(date, name, email, vehicle);
    }

    public LeaseContract() {
    }

    public double getEXPECTED_ENDING_VALUE_PERCENTILE() {
        return EXPECTED_ENDING_VALUE_PERCENTILE;
    }
    public double getLEASE_FEE() {
        return LEASE_FEE;
    }
    public double getFINANCING_PERCENTILE() {
        return FINANCING_PERCENTILE;
    }
    public double getExpectedEndingValue(double price){
        return price * getEXPECTED_ENDING_VALUE_PERCENTILE();
    }
    public double getLeaseFee(double price){
        return price * getLEASE_FEE();
    }
    public double getFinancingTotal(double total){

        return total * getFINANCING_PERCENTILE();
    }

    @Override
    public double getTotalPrice(){
        double price = vehicle.getPrice();

        totalPrice = (getExpectedEndingValue(price)) + (getLeaseFee(price));
        return totalPrice;
    }

    @Override
    public double getMonthlyPayment(){
        double total = getTotalPrice();

        monthlyPayment = (getFinancingTotal(total)) / 36;

        return monthlyPayment;
    }
}
