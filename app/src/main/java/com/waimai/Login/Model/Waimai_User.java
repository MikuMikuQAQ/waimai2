package com.waimai.Login.Model;

import org.litepal.crud.LitePalSupport;

public class Waimai_User extends LitePalSupport implements IWaimai_User {

    private int Id;
    private String username;
    private String password;
    private double balance;
    private int integral;
    private boolean status;

    public Waimai_User() {
    }

    public Waimai_User(int id, String username, String password, double balance, int integral, boolean status) {
        Id = id;
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.integral = integral;
        this.status = status;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
