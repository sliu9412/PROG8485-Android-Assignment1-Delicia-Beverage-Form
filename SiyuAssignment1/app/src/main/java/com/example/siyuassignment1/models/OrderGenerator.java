package com.example.siyuassignment1.models;

import java.util.HashMap;

public class OrderGenerator {
    private final String customer_name;
    private final String email_address;
    private final String phone_number;
    private final String beverage_type;
    private final boolean additional_mike;
    private final boolean additional_sugar;
    private final String beverage_size;
    private final String flavouring;
    private final double tax_rate = 0.13;
    private final HashMap<String, Double> additional = new HashMap<>();
    private final HashMap<String, Double> tea_size = new HashMap<>();
    private final HashMap<String, Double> coffee_size = new HashMap<>();
    private final HashMap<String, Double> flavourings = new HashMap<>();
    private final String City;
    private final String Store;
    private final String sale_date;

    public OrderGenerator(String customer_name, String email_address, String phone_number, String beverage_type, boolean additional_mike, boolean additional_sugar, String beverage_size, String flavouring, String City, String Store, String sale_date) {
        this.customer_name = customer_name;
        this.email_address = email_address;
        this.phone_number = phone_number;
        this.beverage_type = beverage_type;
        this.additional_mike = additional_mike;
        this.additional_sugar = additional_sugar;
        this.beverage_size = beverage_size;
        this.flavouring = flavouring;
        this.City = City;
        this.Store = Store;
        this.sale_date = sale_date;
        SetValues();
    }

    public String Generate() {
        String print_string = "";
        print_string += "Custom Name: " + customer_name + "\n";
        print_string += "Email Address: " + email_address + "\n";
        print_string += "Phone Number: " + phone_number + "\n";
        print_string += "Beverage Type: " + beverage_type + "\n";
        print_string += "Add Milk: " + (additional_mike ? "Yes" : "No") + "\n";
        print_string += "Add Sugar: " + (additional_sugar ? "Yes" : "No") + "\n";
        print_string += "Beverage Size: " + beverage_size + "\n";
        print_string += "Flavouring: " + flavouring + "\n";
        print_string += "City: " + City + "\n";
        print_string += "Store: " + Store + "\n";
        print_string += "Sale Date: " + sale_date + "\n";
        print_string += "Price: $" + PriceCalculate() + "\n";
        return print_string;
    }

    private String PriceCalculate() {
        HashMap<String, Double> size = new HashMap<>();
        if (beverage_type.equals("coffee")) {
            size = coffee_size;
        } else if (beverage_type.equals("tea")) {
            size = tea_size;
        }
        Double total_price = size.get(beverage_size) + (additional_mike ? additional.get("milk") : 0) + (additional_sugar ? additional.get("sugar") : 0) + flavourings.get(flavouring);
        return String.format("%.2f", total_price * (1 + tax_rate));
    }

    private void SetValues() {
        // additional
        this.additional.put("milk", 1.25);
        this.additional.put("sugar", 1.0);
        // sizes
        this.tea_size.put("small", 1.5);
        this.tea_size.put("medium", 2.5);
        this.tea_size.put("large", 3.25);
        this.coffee_size.put("small", 1.75);
        this.coffee_size.put("medium", 2.75);
        this.coffee_size.put("large", 3.75);
        // flavouring
        this.flavourings.put("None", 0.0);
        this.flavourings.put("Lemon", 0.25);
        this.flavourings.put("Ginger", 0.75);
        this.flavourings.put("Pumpkin Spice", 0.5);
        this.flavourings.put("Chocolate", 0.75);
    }


}
