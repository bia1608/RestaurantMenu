package org.example;
import java.util.Map;

public class Order {
    private static final double TVA = 0.09;

    private int orderId;
    private Map<Product, Integer> products;

    public Order(int orderId, Map<Product, Integer> products) {
        this.orderId = orderId;
        this.products = products;
    }

    public void PrintOrderDetails() {
        System.out.println(orderId + ". ");
        System.out.println("Products:");
        for (var entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            System.out.printf("- %s x%d: %.2f RON each\n", product.getName(), quantity, product.getPrice());
        }
        System.out.printf("Total Cost: %.2f RON\n", OrderCost());
    }

    public void PrintOrderDetailsTVA() {
        System.out.println(orderId + ". ");
        System.out.println("Products:");
        for (var entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            System.out.printf("- %s x%d: %.2f RON each\n", product.getName(), quantity, product.getPrice());
        }
        double subtotal = OrderCost();
        double totalWithTax = subtotal * (1.0 + TVA);
        System.out.printf("Subtotal: %.2f RON\n", subtotal);
            System.out.printf("VAT: %.2f%%\n", TVA * 100);
        System.out.printf("Total Cost (with VAT): %.2f RON\n", totalWithTax);
    }

    public double OrderCost() {
        double total = 0;
        for (var entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            total += product.getPrice() * quantity;
        }
        return total;
    }
}
