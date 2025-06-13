package com.CarDealership.application;

import com.CarDealership.ui.UserInterface;

public class Application {

    public void start() {
        UserInterface userInterface = new UserInterface();
        userInterface.loadDealership();
        userInterface.display();
    }
}
