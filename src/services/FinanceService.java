package services;

import enums.Category;
import models.records.TransactionRecord;

import java.util.List;

public class FinanceService {

    public double calculateExpenses(List<TransactionRecord> transactions) {

        return transactions.stream()
                .filter(t -> t.category() != Category.SALARY)
                .mapToDouble(TransactionRecord::price)
                .sum();
    }

    public double calculateSalary(List<TransactionRecord> transactions) {

        return transactions.stream()
                .filter(t -> t.category() == Category.SALARY)
                .mapToDouble(TransactionRecord::price)
                .sum();
    }
}
