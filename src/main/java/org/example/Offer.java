package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Offer {
    private final String description;
    private final BigDecimal discountPercent; // 0..100, stored as BigDecimal
    private final OfferType offerType;

    public enum OfferType {
        FOOD, DRINK, GENERAL
    }

    public Offer(String description, BigDecimal discountPercent, OfferType offerType) {
        this.description = Objects.requireNonNull(description, "description is required");
        this.discountPercent = Objects.requireNonNull(discountPercent, "discountPercent is required")
                .stripTrailingZeros();
        if (this.discountPercent.compareTo(BigDecimal.ZERO) < 0 ||
                this.discountPercent.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("discountPercent must be between 0 and 100");
        }
        this.offerType = Objects.requireNonNull(offerType, "offerType is required");
    }

    public static Offer percent(String description, double percent, OfferType type) {
        return new Offer(description, BigDecimal.valueOf(percent), type);
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public OfferType getOfferType() {
        return offerType;
    }

    /**
     * Returns true if this offer applies to an item described by its categories.
     */
    public boolean appliesTo(boolean isFood, boolean isDrink) {
        switch (offerType) {
            case GENERAL: return true;
            case FOOD: return isFood;
            case DRINK: return isDrink;
            default: return false;
        }
    }

    /**
     * Apply the percentage discount to a price and return the resulting price (rounded to 2 decimals).
     */
    public BigDecimal applyToPrice(BigDecimal price) {
        Objects.requireNonNull(price, "price is required");

        BigDecimal multiplier = BigDecimal.ONE.subtract(
                discountPercent.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP));
        return price.multiply(multiplier).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        String percentStr = discountPercent.stripTrailingZeros().toPlainString();

        StringBuilder sb = new StringBuilder(description)
                .append(" (")
                .append(percentStr)
                .append("%)")
                .append(" - Applies to: ")
                .append(offerType);

        return sb.toString();
    }
}
