package com.CarDealership.ui;

import com.CarDealership.contract.ContractFileManager;
import com.CarDealership.contract.LeaseContract;
import com.CarDealership.contract.SalesContract;

public class ContractMediator {

    public static SalesContract displayAndReturnSaleContract(){
            SalesContract salesContract = ContractPrompter.getSaleContract();
            ContractFileManager.saveSaleContractData(salesContract);

            return salesContract;

    }

    public static LeaseContract displayAndReturnLeaseContract(){
        LeaseContract leaseContract = ContractPrompter.getLeaseContract();
        ContractFileManager.saveLeaseContractData(leaseContract);

        return leaseContract;
    }

}
