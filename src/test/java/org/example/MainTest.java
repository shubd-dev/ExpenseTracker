package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Mock
    private ExpenseUtils mockExpenseUtils;
    private List<Expense> testExpenses;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks if required
        testExpenses = new ArrayList<>();
        testExpenses.add(new Expense(1, LocalDate.of(2025, 1, 1), "Groceries", 100.0));
        testExpenses.add(new Expense(2, LocalDate.of(2025, 2, 1), "Utilities", 200.0));
    }


    @Test
    void testAddExpense() {
        // Arrange
        String input = "3\n2025-03-01\nRent\n300.0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        // Act
        Main.addExpense(testExpenses, scanner);

        // Assert
        Expense addedExpense = testExpenses.get(2);
        assertEquals(3, testExpenses.size());
        assertEquals("Rent", addedExpense.description());
        assertEquals(300.0, addedExpense.amount());
        assertEquals(LocalDate.of(2025, 3, 1), addedExpense.date());
    }



    @Test
    void testUpdateExpense() {
        // Arrange
        String input = "1\n2025-01-02\nGroceries Updated\n150.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Act
        Main.updateExpense(testExpenses, scanner);

        // Assert
        Expense updatedExpense = testExpenses.get(0); // Get the updated expense
        assertAll("Expense Update Assertions",
                () -> assertEquals("Groceries Updated", updatedExpense.description(), "Description should be updated."),
                () -> assertEquals(150.0, updatedExpense.amount(), "Amount should be updated."),
                () -> assertEquals(LocalDate.of(2025, 1, 2), updatedExpense.date(), "Date should be updated.")
        );
    }

    @Test
    void testDeleteExpense() {
        // Arrange
        String input = "1\n";  // Assume user wants to delete expense with id 1
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Act
        Main.deleteExpense(testExpenses, scanner);

        // Assert
        assertEquals(1, testExpenses.size(), "There should be one expense left after deletion.");
        assertEquals(2, testExpenses.get(0).id(), "The remaining expense should have id 2.");
    }

    @Test
    void testViewExpenses() {
        // Redirect console output to verify printed results
        String expectedOutput = "1: Groceries - 100.0\r\n2: Utilities - 200.0\r\n";

        // Capture the printed output
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        // Act
        Main.viewExpenses(testExpenses);

        // Assert
        assertEquals(expectedOutput.trim(), outContent.toString().trim());
    }

    @Test
    void testViewSummary() {
        // Redirect console output to verify printed results
        String expectedOutput = "Total Expenses: 300.0";

        // Capture the printed output
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        // Act
        Main.viewSummary(testExpenses);

        // Assert
        assertTrue(outContent.toString().trim().contains(expectedOutput));
    }

    @Test
    void testViewSummaryByMonth() {
        // Mock user input
        String input = "2025\n1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Redirect console output to verify printed results
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        // Act
        Main.viewSummaryByMonth(testExpenses, scanner);

        // Assert
        assertTrue(outContent.toString().contains("Groceries"));
        assertTrue(outContent.toString().contains("100.0"));
    }

    @AfterEach
    void tearDown() {
        // Cleanup: clear the JSON file or reset state
        clearJsonFile();
    }

    // Helper method to clear the contents of the JSON file
    private void clearJsonFile() {
        // Specify the path to your JSON file
        File jsonFile = new File("expense.json");

        // Overwrite the file with an empty array or object (depending on your structure)
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write("");  // Writes an empty array to the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
