package services;

import enums.Category;
import records.Transaction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    public static List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        try {
            File file = new File("My_finances.csv");

            if (file.exists()) {
                FileReader fileReader = new FileReader(file);
                BufferedReader myBufferedReader = new BufferedReader(fileReader);
                String line;

                while ((line = myBufferedReader.readLine()) != null) {

                    String[] pieces = line.split(",");

                    String descRead = pieces[0];
                    String typeRead = pieces[1];
                    double priceRead = Double.parseDouble(pieces[2]);
                    Category categoryRead = Category.valueOf(pieces[3]);

                    transactions.add(new Transaction(descRead, typeRead, priceRead, categoryRead));

                }
                myBufferedReader.close();
                System.out.println("✅ Data loaded! (" + transactions.size() + " Transactions founded)\n");
            }
        } catch (Exception erro) {
            System.out.println("❌ Error reading file : " + erro.getMessage());
        }
        return transactions;
    }

    public static void deleteTransactions(String fileDelete) {
        File file = new File(fileDelete);

        if (file.delete()) {
            System.out.println("Deleted the file: " + file.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }

    }

    public static void saveTransactions(List<Transaction> transactions) {

        try {
            FileWriter myWriter = new FileWriter("My_finances.csv", false);
            BufferedWriter myBufferedWriter = new BufferedWriter(myWriter);

            for (Transaction t : transactions) {

                String line = t.description() + "," + t.type() + "," + t.price() + "," + t.category();

                myBufferedWriter.write(line);
                myBufferedWriter.newLine();
            }

            myBufferedWriter.close();
            System.out.println("✅ Data saved successfully: 'My_finances.csv'");
        } catch (IOException erro) {
            System.out.println("Error writing to file" + erro.getMessage());
        }
    }
}
