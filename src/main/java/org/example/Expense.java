package org.example;

import java.time.LocalDate;

public class Expense {
    private int id;
    private LocalDate date;
    private String description;
    private double amount;

    public Expense(int id, String date, String description, double amount){
        this.id = id;
        this.date = LocalDate.parse(date);
        this.description = description;
        this.amount = amount;
    }

    //getter and setter method for all the data in the model
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDate() { return date.toString(); }
    public void setDate(String date) { this.date = LocalDate.parse(date); }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
