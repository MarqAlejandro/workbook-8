package com.CarDealership.contract;

import com.CarDealership.model.Vehicle;

public class SalesContract extends Contract{

    private final double SALES_TAX = 1.05;
    private final double RECORDING_FEE = 100;
    private final double PROCESSING_FEE_U10K = 295;
    private final double PROCESSING_FEE_O10K = 495;
  //private double processingFee;
    private boolean isFinancing;
    private final double LOANRATE_O10K = 1.0425;
    private final double LOANRATE_U10K = 1.0525;


    public SalesContract(String date, String name, String email, Vehicle vehicle, boolean isFinancing) {         //double processingFee was taken out of the constructor for testing purposes
        super(date, name, email, vehicle);
  //      this.processingFee = processingFee;
        this.isFinancing = isFinancing;

    }

    public SalesContract() {
        super();
        this.isFinancing = false;
    }

    public double getSALES_TAX() {
        return SALES_TAX;
    }
    public double getRECORDING_FEE() {
        return RECORDING_FEE;
    }
    public double getPROCESSING_FEE_U10K() {
        return PROCESSING_FEE_U10K;
    }
    public double getPROCESSING_FEE_O10K() {
        return PROCESSING_FEE_O10K;
    }
    public double getLOANRATE_U10K() {
        return LOANRATE_U10K;
    }
    public double getLOANRATE_O10K() {
        return LOANRATE_O10K;
    }
    /*    public double getProcessingFee() {
                return processingFee;
            }

          //  public void setProcessingFee(double processingFee) {

                this.processingFee = processingFee;
            }


         */ //processing fee setters and getters in case making it a Final value doesn't work
    public boolean isFinancing() {
        return isFinancing;
    }
    public void setFinancing(boolean financing) {
        isFinancing = financing;
    }

    public double getSalesTax(double price){
        return price * getSALES_TAX();
    }
    public double getJustSalesTax(double price){
        return price * (getSALES_TAX() - 1);
    }

    public double getMonthlyPayment(double total){
        if (total >= 10_000) {
            return (total * getLOANRATE_O10K()) / 48;
        }
        else{
            return (total * getLOANRATE_U10K()) / 24;
        }
    }

    @Override
    public double getTotalPrice(){              //add formula for getting total price
        double price = vehicle.getPrice();
        if(price < 10_000) {
            totalPrice = (getSalesTax(price)) + getRECORDING_FEE() + getPROCESSING_FEE_U10K() ;
        }
        else{
            totalPrice = (getSalesTax(price)) + getRECORDING_FEE() + getPROCESSING_FEE_O10K() ;
        }
        return totalPrice;
    }

    @Override
    public double getMonthlyPayment(){         //if not financing then calculate monthly payment, else if financing then calculate different monthly payment
        if(isFinancing) {
            double total = getTotalPrice();

            monthlyPayment = getMonthlyPayment(total);
        }
        else{
            monthlyPayment = 0;
        }
        return monthlyPayment;
    }


}
