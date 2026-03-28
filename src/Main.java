import enums.Category;
import models.records.TransactionRecord;
import services.FileService;
import services.FinanceService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        FinanceService financeService = new FinanceService();

        String description, choice, answer, type, filename = "";
        double price = 0;
        Category category = null;

        System.out.println("Do you want to create a new file? (Y/N)");
        answer = scanner.nextLine();

        if (answer.equalsIgnoreCase("Y")) {
            System.out.println("Please enter the name of your file");
            filename = scanner.nextLine();
        }

        List<TransactionRecord> transactions = FileService.loadTransactions(filename);

        do {
            System.out.println("====================================");
            System.out.println("*** My Finance Management System ***");
            System.out.println("====================================");
            System.out.println("""
                    1. Add Transaction\s
                    2. View Finance Summary
                    3. Exit
                    4. Delete""");
            System.out.println("====================================");

            System.out.print("Please enter your choice: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("\nEnter Description: ");
                    description = scanner.nextLine();

                    System.out.print("Enter Type: ");
                    type = scanner.nextLine();

                    try {
                        System.out.print("Enter Price: ");
                        price = scanner.nextDouble();
                        scanner.nextLine();

                        System.out.println("Enter Category: Food, Transport, Health or Salary");
                        category = Category.valueOf(scanner.nextLine().toUpperCase());

                        transactions.add(new TransactionRecord(description, type, price, category));
                        System.out.println();

                    } catch (InputMismatchException inputMismatchException) {
                        System.out.println("ERROR: The price must be numbers");
                        scanner.nextLine();

                    } catch (IllegalArgumentException illegalArgumentException) {
                        System.out.println("ERROR: The category must be: FOOD, TRANSPORT, HEALTH or SALARY");
                    }
                    break;
                case "2":
                    double calcExpenses = financeService.calculateExpenses(transactions);
                    double salary = financeService.calculateSalary(transactions);
                    System.out.println("Your expenses: " + calcExpenses);
                    System.out.println("Your salary: " + salary);
                    System.out.println("Total: " + (salary - calcExpenses) + "\n");
                    break;
                case "3":
                    choice = "exit";
                    FileService.saveTransactions(transactions, filename);
                    break;
                case "4":
                    System.out.print("\nName of the file to delete: ");
                    filename = scanner.nextLine();
                    FileService.deleteTransactions(filename + ".csv");
                    transactions.clear();
                    System.out.println("Do you want to exit? y/n");
                    choice = scanner.nextLine();

                    if (Objects.equals(choice, "y")) {
                        choice = "exit";
                    }
                    break;
                default:
                    System.out.println("ERROR: Please enter a valid choice");
                    break;
            }
        }
        while (!choice.equalsIgnoreCase("exit"));

    }
}
