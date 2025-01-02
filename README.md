
# Expense Tracker CLI Application

This is a simple command-line interface (CLI) application that allows users to manage their expenses. It provides functionality to add, update, delete, and view expenses. It also generates a summary of expenses for a specific month or the entire dataset.

🚀 **Project URL**: [Expense Tracker Roadmap](https://roadmap.sh/projects/expense-tracker)

## Features 🌟
- **Add Expense**: Add new expenses with date, description, and amount.
- **Update Expense**: Update details of an existing expense.
- **Delete Expense**: Delete an expense based on its ID.
- **View All Expenses**: View all added expenses.
- **View Summary**: View the total expenses.
- **View Monthly Summary**: View expenses for a specific month and year.

## Technologies Used ⚙️
- Java
- JUnit (for testing)
- Mockito (for mocking dependencies)
- Local file storage (JSON file format)

## Getting Started 🏁

### Prerequisites
Before running the application, ensure you have the following installed on your system:

- Java 11 or higher
- JUnit 5 (for testing)
- Maven (build tool)

### Setup

1. **Clone the repository:**

   ```bash
   git clone https://github.com/your-username/expense-tracker-cli.git
   cd expense-tracker-cli
   ```

2. **Build the project:**

   If you're using **Maven**:

   ```bash
   mvn clean install
   ```

3. **Run the Application:**

   To run the application, execute the `Main` class:

   ```bash
   java -cp target/expense-tracker-cli-1.0-SNAPSHOT.jar org.example.Main
   ```

   This will start the expense tracker CLI, where you can interact with the application by choosing options from the menu.

## Usage 🖥️

The application provides the following options in the CLI:

1. **Add Expense**: Enter an expense ID, date, description, and amount.
2. **Update Expense**: Enter the expense ID and update details like date, description, and amount.
3. **Delete Expense**: Enter the expense ID to delete it.
4. **View All Expenses**: Displays all the expenses in the system.
5. **View Summary of All Expenses**: Displays the total expenses across all entries.
6. **View Summary by Specific Month**: Enter a year and month to view a summary of expenses for that month.
7. **Exit**: Save the changes to the expenses and exit the application.

### Example Interaction

```
Welcome to the Expense Tracker
1. Add Expense
2. Update Expense
3. Delete Expense
4. View Expense
5. View Summary of All Expenses
6. View Summary of Specific Month
7. Exit
Enter your choice: 1
Enter ID: 3
Enter Date (YYYY-MM-DD): 2025-03-01
Enter Description: Rent
Enter Amount: 300.0
Expense added successfully!
```

## Testing 🔍

### Running Unit Tests

To run the unit tests, ensure that you have Maven installed and run the following command:

```bash
mvn test
```

This will run the unit tests for the application, including the functions for adding, updating, deleting, and viewing expenses.

### Mocking File Writes

The application uses a local JSON file to persist expenses. During testing, you can mock the file operations using `Mockito` to avoid writing to the file.

### Testing Frameworks Used
- **JUnit 5**: For unit testing.
- **Mockito**: For mocking dependencies (e.g., file operations).

## License 📜

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact ✉️

For any inquiries or issues, please feel free to open an issue or contact me via [shubhamdhumale@gmail.com].
