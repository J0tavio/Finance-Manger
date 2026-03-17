package services;

import enums.Category;
import records.Transaction;

import java.util.List;

public class FinanceService {

    public double calculateExpenses(List<Transaction> transactions) {

        return transactions.stream()
                .filter(t -> t.category() != Category.SALARY)
                .mapToDouble(Transaction::price)
                .sum();
    }

    public double calculateSalary(List<Transaction> transactions) {

        return transactions.stream()
                .filter(t -> t.category() == Category.SALARY)
                .mapToDouble(Transaction::price)
                .sum();
    }
}
