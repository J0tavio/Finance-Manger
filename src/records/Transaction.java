package records;

import enums.Category;

public record Transaction(String description, String type, double price, Category category) {
}
