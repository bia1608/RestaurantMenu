package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Product> menu = new ArrayList<>(List.of(
                new Food("Margherita Pizza", 45.0, 450),
                new Food("Carbonara Pasta", 52.5, 400),
                new Drink("Lemonade", 15.0, 400),
                new Drink("Bottled Water", 8.0, 500)
        ));

        List<Offer> offers = new ArrayList<>();
        List<Boolean> offerEnabled = new ArrayList<>(); // parallel flags, default disabled
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println("\n--- Restaurant Menu Options ---");
            System.out.println("1. Display menu");
            System.out.println("2. Add product");
            System.out.println("3. Add special offer");
            System.out.println("4. Apply (enable) special offer");
            System.out.println("5. Toggle offer enabled/disabled");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1" -> {
                    displayMenu(menu, offers, offerEnabled);
                    if (!offers.isEmpty()) {
                        System.out.println("\nOffers:");
                        for (int i = 0; i < offers.size(); i++) {
                            String status = (i < offerEnabled.size() && Boolean.TRUE.equals(offerEnabled.get(i))) ? "ENABLED" : "DISABLED";
                            System.out.println((i + 1) + ". " + offers.get(i) + " [" + status + "]");
                        }
                    }
                }

                case "2" -> {
                    System.out.print("Product type (food/drink): ");
                    String type = scanner.nextLine().trim().toLowerCase();
                    System.out.print("Product name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Price (e.g. 12.5): ");
                    double price;

                    try {
                        price = Double.parseDouble(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid price. Operation cancelled.");
                        break;
                    }

                    if (type.equals("food")) {
                        System.out.print("Quantity (int): ");
                        try {
                            int cal = Integer.parseInt(scanner.nextLine().trim());
                            menu.add(new Food(name, price, cal));
                            System.out.println("Food added successfully.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid calories value. Operation cancelled.");
                        }
                    } else if (type.equals("drink")) {
                        System.out.print("Volume (ml, int): ");
                        try {
                            int vol = Integer.parseInt(scanner.nextLine().trim());
                            menu.add(new Drink(name, price, vol));
                            System.out.println("Drink added successfully.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid volume value. Operation cancelled.");
                        }
                    } else
                        System.out.println("Unknown type. Use 'food' or 'drink'.");
                }

                case "3" -> {
                    System.out.print("Offer description (e.g. HappyHour drinks -20%): ");
                    String desc = scanner.nextLine().trim();
                    System.out.print("Discount percent: ");
                    double pct;
                    try {
                        pct = Double.parseDouble(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid percent value. Operation cancelled.");
                        break;
                    }

                    System.out.print("Offer target (food/drink/general): ");
                    String target = scanner.nextLine().trim().toUpperCase();

                    Offer.OfferType offerType;
                    try {
                        offerType = Offer.OfferType.valueOf(target);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid target. Use 'food', 'drink' or 'general'. Operation cancelled.");
                        continue;
                    }

                    try {
                        offers.add(Offer.percent(desc, pct, offerType));
                        offerEnabled.add(false); // new offers start disabled
                        System.out.println("Offer added (initially DISABLED): " + desc + " (" + pct + "%) - " + offerType);
                    } catch (IllegalArgumentException iae) {
                        System.out.println("Invalid offer data: " + iae.getMessage());
                    }
                }

                case "4" -> {
                    if (offers.isEmpty()) {
                        System.out.println("No offers to enable.");
                        break;
                    }
                    System.out.println("\nSelect an offer to enable:");
                    for (int i = 0; i < offers.size(); i++) {
                        String status = (i < offerEnabled.size() && Boolean.TRUE.equals(offerEnabled.get(i))) ? "ENABLED" : "DISABLED";
                        System.out.println((i + 1) + ". " + offers.get(i) + " [" + status + "]");
                    }
                    System.out.print("Offer number: ");
                    int offerIdx;
                    try {
                        offerIdx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Operation cancelled.");
                        break;
                    }
                    if (offerIdx < 0 || offerIdx >= offers.size()) {
                        System.out.println("Offer index out of range. Operation cancelled.");
                        break;
                    }
                    offerEnabled.set(offerIdx, true);
                    System.out.println("Offer enabled: " + offers.get(offerIdx));
                    System.out.println("\nMenu with discounts applied:");
                    displayMenu(menu, offers, offerEnabled);
                }

                case "5" -> {
                    if (offers.isEmpty()) {
                        System.out.println("No offers to toggle.");
                        break;
                    }
                    System.out.println("\nSelect an offer to toggle enabled/disabled:");
                    for (int i = 0; i < offers.size(); i++) {
                        String status = (i < offerEnabled.size() && Boolean.TRUE.equals(offerEnabled.get(i))) ? "ENABLED" : "DISABLED";
                        System.out.println((i + 1) + ". " + offers.get(i) + " [" + status + "]");
                    }
                    System.out.print("Offer number: ");
                    int idx;
                    try {
                        idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Operation cancelled.");
                        break;
                    }
                    if (idx < 0 || idx >= offers.size()) {
                        System.out.println("Offer index out of range. Operation cancelled.");
                        break;
                    }
                    boolean now = !Boolean.TRUE.equals(offerEnabled.get(idx));
                    offerEnabled.set(idx, now);
                    System.out.println((now ? "Enabled" : "Disabled") + " offer: " + offers.get(idx));
                    System.out.println("\nMenu " + (now ? "with" : "without") + " discounts:");
                    displayMenu(menu, offers, offerEnabled);
                }

                case "0" -> {
                    running = false;
                    System.out.println("Exiting. Goodbye!");
                }
                default -> System.out.println("Unknown option. Try again.");
            }
        }

        scanner.close();
    }

    private static void displayMenu(List<Product> menu, List<Offer> offers, List<Boolean> offerEnabled) {
        System.out.println("\n---- \"La Andrei\" Restaurant Menu ----\n");
        for (int i = 0; i < menu.size(); i++) {
            Product p = menu.get(i);
            System.out.print((i + 1) + ". ");
            p.WriteInfo();

            boolean isFood = p instanceof Food;
            boolean isDrink = p instanceof Drink;

            BigDecimal original;
            try {
                original = BigDecimal.valueOf(p.getPrice()).setScale(2, RoundingMode.HALF_UP);
            } catch (NoSuchMethodError | AbstractMethodError e) {
                System.out.println("  Price: (unavailable)");
                continue;
            }

            BigDecimal best = original;
            int bestOfferIdx = -1;
            for (int j = 0; j < offers.size(); j++) {
                if (j >= offerEnabled.size() || !Boolean.TRUE.equals(offerEnabled.get(j))) continue;
                Offer of = offers.get(j);
                if (!of.appliesTo(isFood, isDrink)) continue;
                BigDecimal discounted = of.applyToPrice(original);
                if (discounted.compareTo(best) < 0) {
                    best = discounted;
                    bestOfferIdx = j;
                }
            }

            if (bestOfferIdx >= 0) {
                System.out.println("  Price: " + original + " -> " + best + "  (Offer: " + offers.get(bestOfferIdx).getDescription() + ")");
            } else {
                System.out.println("  Price: " + original);
            }
        }
        System.out.println("\n-------------------------------------");
    }
}
