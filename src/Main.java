import enums.Category;
import records.Transaction;
import services.FileService;
import services.FinanceService;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static services.FileService.deleteTransactions;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Transaction> transactions = FileService.loadTransactions();

        FinanceService financeService = new FinanceService();

        String description = "";
        String type = "";
        double price = 0;
        Category category = null;
        String answer = "";

        System.out.println("====================================");
        System.out.println("*** My Finance Management System ***");
        System.out.println("====================================");
        System.out.println("""
                1. Add Transaction\s
                2. View Finance Summary
                3. Exit
                4. Delete""");
        System.out.println("====================================");


        do {
            System.out.print("Please enter your choice: ");
            answer = scanner.nextLine();

            switch (answer) {
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

                        transactions.add(new Transaction(description, type, price, category));
                        System.out.println();

//                        System.out.println("Do you want to add an item to the list? (Y/N)");
//                        answer = scanner.nextLine();

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
                    answer = "exit";
                    FileService.saveTransactions(transactions);
                    break;
                case "4":
                    FileService.deleteTransactions("My_finances.csv");
                    answer = "exit";
                    break;
                default:
                    System.out.println("ERROR: Please enter a valid choice");
                    break;

            }
        }
        while (!answer.equalsIgnoreCase("exit"));


    }
}
