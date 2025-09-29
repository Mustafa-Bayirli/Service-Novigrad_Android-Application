package com.example.javaClasses;

import android.app.Application;

public class GlobalVariables extends Application{

    private String username;
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    private String password;
    public void setPassword(String password) { this.password = password; }
    public String getPassword() { return password; }

    private String serviceWanted;
    public void setServiceWanted(String serviceWanted) {
        this.serviceWanted = serviceWanted;
    }
    public String getServiceWanted() {
        return serviceWanted;
    }
}
