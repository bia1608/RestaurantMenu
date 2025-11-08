package org.example;

public class Food extends Product {
    private int grams;

    public Food(String name, double price, int grams) {
        super(name, price);
        this.grams = grams;
    }

    @Override
    public void WriteInfo() {
        System.out.printf("%s - %.2f RON - Gramaj: %dg\n", this.getName(), this.getPrice(), this.grams);
    }

    public int getGrams() {
        return grams;
    }

    public void setGrams(int grams) {
        this.grams = grams;
    }
}
