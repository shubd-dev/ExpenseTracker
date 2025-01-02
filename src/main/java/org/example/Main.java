package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome to the Expense Tracker");
            System.out.println("1. Add Expense");
            System.out.println("2. Update Expense");
            System.out.println("3. Delete Expense");
            System.out.println("4. View Expense");
            System.out.println("5. View Summary of All Expenses");
            System.out.println("6. View Summary of specific month");
            System.out.println("7. Exit");


            Scanner s = new Scanner(System.in);
            Integer choice = s.nextInt();
            List<expense> expenses = expenseUtils.loadExpenses();

            switch (choice) {
                case 1:
                    //adding expense
                    addExpense(expenses, s);
                    break;
                case 2:
                    //update expense
                    updateExpense(expenses, s);
                    break;
                case 3:
                    //delete expense
                    deleteExpense(expenses, s);
                    break;
                case 4:
                    viewExpenses(expenses);
                    break;
                case 5:
                    //view summary
                    viewSummary(expenses);
                    break;
                case 6:
                    //view summary of specific month
                    viewSummaryByMonth(expenses, s);
                    break;
                case 7:
                    expenseUtils.saveExpenses(expenses);
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void viewSummaryByMonth(List<expense> expenses, Scanner scanner) {
        System.out.print("Enter year (YYYY): ");
        int year = scanner.nextInt();
        System.out.print("Enter month (1-12): ");
        int month = scanner.nextInt();

        // Validate month input
        if (month < 1 || month > 12) {
            System.out.println("Invalid month. Please enter a value between 1 and 12.");
            return;
        }

        // Filter expenses for the given year and month
        double total = 0.0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        boolean foundExpenses = false;

        for (expense expense : expenses) {
            try {
                LocalDate expenseDate = LocalDate.parse(expense.getDate(), formatter);
                if (expenseDate.getYear() == year && expenseDate.getMonthValue() == month) {
                    // Print details of the expense
                    System.out.println("ID: " + expense.getId() +
                            ", Description: " + expense.getDescription() +
                            ", Amount: " + expense.getAmount());
                    total += expense.getAmount();
                    foundExpenses = true;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format in expense data: " + expense.getDate());
            }
        }

        if (foundExpenses) {
            System.out.println("Total Expenses for " + year + "-" + String.format("%02d", month) + ": " + total);
        } else {
            System.out.println("No expenses found for " + year + "-" + String.format("%02d", month));
        }
    }


    static void viewSummary(List<expense> expenses) {
        double total = expenses.stream().mapToDouble(expense::getAmount).sum();
        System.out.println("Total Expenses: " + total);
    }

    static void viewExpenses(List<expense> expenses) {
        if (expenses.isEmpty()) {
            System.out.println("No expenses to show.");
            return;
        }
        for (expense expense : expenses) {
            System.out.println(expense.getId() + ": " + expense.getDescription() + " - " + expense.getAmount());
        }
    }

    static void deleteExpense(List<expense> expenses, Scanner s) {
        System.out.print("Enter Expense ID to Delete: ");
        int id = s.nextInt();

        expenses.removeIf(expense -> expense.getId() == id);
        expenseUtils.saveExpenses(expenses);
        System.out.println("Expense deleted successfully!");
    }

    static void updateExpense(List<expense> expenses, Scanner scanner) {
        System.out.print("Enter Expense ID to Update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (expense expense : expenses) {
            if (expense.getId() == id) {
                System.out.print("Enter New Date (YYYY-MM-DD): ");
                expense.setDate(scanner.nextLine());
                System.out.print("Enter New Description: ");
                expense.setDescription(scanner.nextLine());
                System.out.print("Enter New Amount: ");
                expense.setAmount(scanner.nextDouble());
                expenseUtils.saveExpenses(expenses);
                System.out.println("Expense updated successfully!");
                return;
            }
        }
        System.out.println("Expense ID not found.");
    }

    static void addExpense(List<expense> expenses, Scanner scanner) {

        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        expenses.add(new expense(id, date, description, amount));
        expenseUtils.saveExpenses(expenses);
        System.out.println("Expense added successfully!");
    }
}

