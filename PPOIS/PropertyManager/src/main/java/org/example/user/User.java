package org.example.user;

import org.example.enums.Building;
import org.example.exceptions.NotEnoughMoneyException;
import org.example.database.DatabaseManager;
import org.example.enums.Employee;

import java.math.BigDecimal;

public class User {
    private final DatabaseManager db = new DatabaseManager();
    private int id;
    private String username;
    private String password;
    private String phoneNumber;
    private String email;
    private java.math.BigDecimal balance;

    public User(int id, String username, String password, String phoneNumber, String email, BigDecimal balance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.balance = balance;
    }
    public boolean addProperty(int square, int floors, String address, String description, java.math.BigDecimal price, Building building){
        return db.addPropertyToDatabase(this.id, square, floors, address, description, price, building);
    }
    public boolean callPlumber(int propertyID) throws NotEnoughMoneyException{
        if(balance.compareTo(Employee.PLUMBER.getPayment())<0){
            throw new NotEnoughMoneyException("Your balance: " + balance + "\nService price: " + Employee.PLUMBER.getPayment());
        }
        balance = balance.subtract(Employee.PLUMBER.getPayment());
        db.updateBalanceForUser(this.id, balance);
        db.addRequestForService(propertyID, Employee.PLUMBER);
        return true;
    }

    public boolean callElectrician(int propertyId) throws NotEnoughMoneyException{
        if(balance.compareTo(Employee.ELECTRICIAN.getPayment())<0){
            throw new NotEnoughMoneyException("Your balance: " + balance + "\nService price: " + Employee.ELECTRICIAN.getPayment());
        }
        balance = balance.subtract(Employee.ELECTRICIAN.getPayment());
        db.updateBalanceForUser(this.id, balance);
        db.addRequestForService(propertyId, Employee.ELECTRICIAN);
        return true;
    }

    public boolean sellProperty(int propertyId){
        if(db.setStatusForProperty(this.id, propertyId, true)){
            return false;
        }
        java.math.BigDecimal price = db.getPropertyPrice(propertyId);
        balance = balance.add(price);
        db.updateBalanceForUser(this.id, balance);
        return true;
    }

    public boolean buyProperty(int propertyId) throws NotEnoughMoneyException {
        java.math.BigDecimal price = db.getPropertyPrice(propertyId);
        if(balance.compareTo(price) < 0){
            throw new NotEnoughMoneyException("Your balance: " + balance + "\nProperty price is: " + price);
        } else if (db.getUserIdFromProperty(propertyId) == this.id) {
            System.out.println("It`s already your property");
            return false;
        }
        balance = balance.subtract(price);
        db.updateBalanceForUser(this.id, balance);
        db.setUserIdForProperty(this.id, propertyId);
        db.setUserIdForProperty(this.id, propertyId);
        db.setStatusForProperty(this.id, propertyId, false);
        return true;
    }
}