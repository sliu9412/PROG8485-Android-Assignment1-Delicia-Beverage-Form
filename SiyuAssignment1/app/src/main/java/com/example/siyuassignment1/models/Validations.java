package com.example.siyuassignment1.models;

import android.view.View;
import android.widget.TextView;

import com.example.siyuassignment1.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Validations {
    public static boolean validate(ActivityMainBinding binding, String beverage_type, Boolean additional_mike, Boolean additional_sugar, String beverage_size, String adding_flavour, String City, String Store) {
        // Customer Name
        String customer_name = binding.customerNameInput.getText().toString();
        if (customer_name.isEmpty()) {
            DisplayMessage(binding, "Customer Name is Empty");
            binding.customerNameInput.setError("Customer Name is Empty");
            return false;
        }

        // Email Address
        String email_address = binding.emailAddressInput.getText().toString();
        Pattern email_regex = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        if (!email_regex.matcher(email_address).matches()) {
            DisplayMessage(binding, "Email Address Format is Incorrect");
            binding.emailAddressInput.setError("Email Address Format is Incorrect");
            return false;
        }

        // Phone Number
        String phone_number = binding.phoneNumberInput.getText().toString();
        Pattern phone_regex = Pattern.compile("^\\d{3}-\\d{3}-\\d{4}$");
        if (!phone_regex.matcher(phone_number).matches()) {
            DisplayMessage(binding, "Phone Format is Incorrect");
            binding.phoneNumberInput.setError("Phone Format is Incorrect");
            return false;
        }

        // Beverage Size
        if (beverage_size.isEmpty()) {
            DisplayMessage(binding, "Beverage Size Should be Selected");
            return false;
        }

        // City
        if (City.isEmpty()) {
            DisplayMessage(binding, "City is Empty or Incorrect");
            return false;
        }

        // Store
        if (Store.isEmpty()) {
            DisplayMessage(binding, "Store Should be Selected");
            return false;
        }

        // Date
        String sale_date = binding.saleDate.getText().toString();
        if (sale_date.isEmpty()) {
            DisplayMessage(binding, "Sale Date Should be Selected");
            binding.saleDate.setError("Sale Date Should be Selected");
            return false;
        } else {
            String[] date_array = sale_date.split("/");
            int year = Integer.parseInt(date_array[0]);
            int month = Integer.parseInt(date_array[1]);
            int day = Integer.parseInt(date_array[2]);
            LocalDate current_date = LocalDate.now();
            LocalDate picked_date = LocalDate.of(year, month, day);
            if (current_date.isBefore(picked_date)) {
                binding.saleDate.setError("Sale Date Should Before the Current Date");
                DisplayMessage(binding, "Sale Date Should Before the Current Date");
                return false;
            }
            binding.saleDate.setError(null);
        }

        OrderGenerator orderGenerator = new OrderGenerator(customer_name, email_address, phone_number, beverage_type, additional_mike, additional_sugar, beverage_size, adding_flavour, City, Store, sale_date);
        DisplayMessage(binding, orderGenerator.Generate());
        return true;
    }

    public static void DisplayMessage(ActivityMainBinding binding, String message) {
        Snackbar snackbar = Snackbar.make(binding.root, message, BaseTransientBottomBar.LENGTH_INDEFINITE);
        View snackbarView = snackbar.getView();
        TextView tv = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setMaxLines(99);
        snackbar.setAction("OK", v -> {
        }).show();
    }
}
