package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class expenseUtils {
    private static final String FILE_PATH = "expense.json";

    public static void saveExpenses(List<expense> expenses){
        JSONArray jsonArray = new JSONArray();

        for(expense ex : expenses){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", ex.getId());
            jsonObject.put("date",ex.getDate());
            jsonObject.put("description",ex.getDescription());
            jsonObject.put("amount",ex.getAmount());
            jsonArray.put(jsonObject);
        }

        try {
            FileWriter file = new FileWriter(FILE_PATH);
            file.write(jsonArray.toString(4));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static List<expense> loadExpenses(){
        List<expense> expenses = new ArrayList<>();
        File file = new File(FILE_PATH);

        if(file.exists() == false){
            System.out.println("File does not exists");
            return expenses;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            StringBuilder jsonText = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonText.append(line);
            }

            JSONArray jsonArray = new JSONArray(jsonText.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                expense expense = new expense(
                        jsonObject.getInt("id"),
                        jsonObject.getString("date"),
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