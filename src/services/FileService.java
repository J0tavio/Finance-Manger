package services;

import enums.Category;
import models.records.TransactionRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    public static List<TransactionRecord> loadTransactions(String fileName) {
        List<TransactionRecord> transactions = new ArrayList<>();

        try {
            File file = new File(fileName + ".csv");

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

                    transactions.add(new TransactionRecord(descRead, typeRead, priceRead, categoryRead));

                }
                myBufferedReader.close();
                System.out.println("✅ Data loaded! (" + transactions.size() + " Transactions founded)\n");
            }
        } catch (Exception error) {
            System.out.println("❌ Error reading file : " + error.getMessage());
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

    public static void saveTransactions(List<TransactionRecord> transactions, String fileName) {

        try {
            FileWriter myWriter = new FileWriter(fileName + ".csv", false);
            BufferedWriter myBufferedWriter = new BufferedWriter(myWriter);

            for (TransactionRecord t : transactions) {

                String line = t.description() + "," + t.type() + "," + t.price() + "," + t.category();

                myBufferedWriter.write(line);
                myBufferedWriter.newLine();
            }

            myBufferedWriter.close();
            System.out.println("✅ Data saved successfully: " + fileName + "'.csv'");
        } catch (IOException error) {
            System.out.println("Error writing to file" + error.getMessage());
        }
    }
}
