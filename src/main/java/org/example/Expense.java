package org.example;

public record Expense(
        int id,
        String date,
        String description,
        double amount
) {
}
