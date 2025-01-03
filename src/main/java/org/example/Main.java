package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public final class Main {
    private Main() {}

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


            boolean validInput = false;
            int choice = 0;
            while(!validInput) {
                String input = s.nextLine();

                try {
                    //this is used to trim any whitespaces and parse it to integer
                    choice = Integer.parseInt(input.trim());
                    validInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                }
            }

            List<Expense> expenses = ExpenseUtils.loadExpenses();

            switch (choice) {
                case 1 ->
                    //adding expense
                    addExpense(expenses, s);
                case 2 ->
                    //update expense
                    updateExpense(expenses, s);
                case 3 ->
                    //delete expense
                    deleteExpense(expenses, s);
                case 4 ->
                    viewExpenses(expenses);
                case 5 ->
                    //view summary
                    viewSummary(expenses);
                case 6 ->
                    //view summary of specific month
                    viewSummaryByMonth(expenses, s);
                case 7 -> {
                    ExpenseUtils.saveExpenses(expenses);
                    System.out.println("Goodbye!");
                    return;
                }
                default ->
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void viewSummaryByMonth(List<Expense> expenses, Scanner scanner) {
        System.out.print("Enter year (YYYY): ");
        int year = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter month (1-12): ");
        int month = Integer.parseInt(scanner.nextLine());

        // Validate month input
        if (month < 1 || month > 12) {
            System.out.println("Invalid month. Please enter a value between 1 and 12.");
            return;
        }

        // Filter expenses for the given year and month
        double total = 0.0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        boolean foundExpenses = false;

        for (Expense expense : expenses) {
            // Validate the date format using the provided formatter
            if (expense.date().getYear() == year && expense.date().getMonthValue() == month) {
                // Print expense details and update the total
                System.out.printf("ID: %d, Description: %s, Amount: %.2f%n",
                        expense.id(), expense.description(), expense.amount());
                total += expense.amount();
                foundExpenses = true;
            }
        }

        if (foundExpenses) {
            System.out.println("Total Expenses for " + year + "-" + String.format("%02d", month) + ": " + total);
        } else {
            System.out.println("No expenses found for " + year + "-" + String.format("%02d", month));
        }
    }


    static void viewSummary(List<Expense> expenses) {
        double total = expenses.stream().mapToDouble(Expense::amount).sum();
        System.out.println("Total Expenses: " + total);
    }

    static void viewExpenses(List<Expense> expenses) {
        if (expenses.isEmpty()) {
            System.out.println("No expenses to show.");
            return;
        }
        for (Expense expense : expenses) {
            System.out.println(expense.id() + ": " + expense.description() + " - " + expense.amount());
        }
    }

    static void deleteExpense(List<Expense> expenses, Scanner s) {
        System.out.print("Enter Expense ID to Delete: ");
        int id = Integer.parseInt(s.nextLine());

        expenses.removeIf(expense -> expense.id() == id);
        ExpenseUtils.saveExpenses(expenses);
        System.out.println("Expense deleted successfully!");
    }

    static void updateExpense(List<Expense> expenses, Scanner scanner) {
        System.out.print("Enter Expense ID to Update: ");
        int id = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < expenses.size(); i++) {
            Expense expense = expenses.get(i);
            if (expense.id() == id) {
                System.out.print("Enter New Date (YYYY-MM-DD): ");
                var dateInput = scanner.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(dateInput, formatter);
                System.out.print("Enter New Description: ");
                var description = scanner.nextLine();
                System.out.print("Enter New Amount: ");
                var amount = Double.parseDouble(scanner.nextLine());
                expenses.set(i, new Expense(
                        expense.id(),
                        date,
                        description,
                        amount
                ));
                ExpenseUtils.saveExpenses(expenses);
                System.out.println("Expense updated successfully!");
                return;
            }
        }
        System.out.println("Expense ID not found.");
    }

    static void addExpense(List<Expense> expenses, Scanner scanner) {

        System.out.print("Enter ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        System.out.print("Enter Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        expenses.add(new Expense(id, localDate, description, amount));
        ExpenseUtils.saveExpenses(expenses);
        System.out.println("Expense added successfully!");
    }
}

