package org.example;

public class Drink extends Product {
    private int milliliters;

    public Drink(String name, double price, int milliliters) {
        super(name, price);
        this.milliliters = milliliters;
    }

    @Override
    void WriteInfo() {
        System.out.println("%s - %.2f RON - Volum: %d ml".formatted(getName(), getPrice(), milliliters));
    }

    public int getMilliliters() {
        return milliliters;
    }

    public void setMilliliters(int milliliters) {
        this.milliliters = milliliters;
    }
}
