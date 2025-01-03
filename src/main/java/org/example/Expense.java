package org.example;
import java.time.LocalDate;


public record Expense(
        int id,
        LocalDate date,
        String description,
        double amount
){}
