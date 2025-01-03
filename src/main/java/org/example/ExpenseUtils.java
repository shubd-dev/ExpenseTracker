package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public final class ExpenseUtils {
    private static final String FILE_PATH = "expense.json";

    private ExpenseUtils() {}

    public static void saveExpenses(List<Expense> expenses){
        JSONArray jsonArray = new JSONArray();

        for(Expense ex : expenses){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", ex.id());
            jsonObject.put("date",ex.date());
            jsonObject.put("description",ex.description());
            jsonObject.put("amount",ex.amount());
            jsonArray.put(jsonObject);
        }

        try(FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(jsonArray.toString(4));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }

    public static List<Expense> loadExpenses(){
        List<Expense> expenses = new ArrayList<>();
        Path path = Path.of(FILE_PATH);

        if(!Files.exists(path)){
            System.out.println("File does not exists");
            return expenses;
        }

        try {
            String jsonText = Files.readString(path);
            JSONArray jsonArray = new JSONArray(jsonText);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                System.out.println(jsonObject.getString("key"));
                Expense expense = new Expense(
                        jsonObject.getInt("id"),
                        LocalDate.parse(jsonObject.getString("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        jsonObject.getString("description"),
                        jsonObject.getDouble("amount")
                );
                expenses.add(expense);
            }
        } catch (IOException e) {
            System.out.println("Error loading expenses: " + e.getMessage());
        }

        return expenses;
    }
}