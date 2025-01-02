package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Mock
    private expenseUtils mockExpenseUtils;
    private List<expense> testExpenses;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testExpenses = new ArrayList<>();
        testExpenses.add(new expense(1, "2025-01-01", "Groceries", 100.0));
        testExpenses.add(new expense(2, "2025-02-01", "Utilities", 200.0));
    }

    @Test
    void testAddExpense() {
        // Mock user input
        String input = "3\n2025-03-01\nRent\n300.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Act
        Main.addExpense(testExpenses, scanner);

        // Assert
        assertEquals(3, testExpenses.size());
        assertEquals("Rent", testExpenses.get(2).getDescription());
        assertEquals(300.0, testExpenses.get(2).getAmount());
    }

    @Test
    void testUpdateExpense() {
        // Mock user input
        String input = "1\n2025-01-02\nGroceries Updated\n150.0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Act
        Main.updateExpense(testExpenses, scanner);

        // Assert
        assertEquals("Groceries Updated", testExpenses.get(0).getDescription());
        assertEquals(150.0, testExpenses.get(0).getAmount());
    }

    @Test
    void testDeleteExpense() {
        // Mock user input
        String input = "1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Act
        Main.deleteExpense(testExpenses, scanner);

        // Assert
        assertEquals(1, testExpenses.size());
        assertEquals(2, testExpenses.get(0).getId());
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
