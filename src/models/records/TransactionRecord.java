package models.records;

import enums.Category;

public record TransactionRecord(String description, String type, double price, Category category) {
}
