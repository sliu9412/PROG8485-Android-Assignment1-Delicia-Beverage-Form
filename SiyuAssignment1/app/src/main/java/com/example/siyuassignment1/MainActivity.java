package com.example.siyuassignment1;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.siyuassignment1.databinding.ActivityMainBinding;
import com.example.siyuassignment1.models.Validations;
import com.example.siyuassignment1.models.dataSource;

import java.util.Calendar;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    ActivityMainBinding binding;
    dataSource data_source;
    // properties
    String beverage_type = "coffee";
    boolean additional_mike;
    boolean additional_sugar;
    String beverage_size = "";
    String adding_flavour = "None";
    String City = "";
    String Store = "";
    DatePickerDialog datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // set data source to handle dynamic adaptor
        data_source = new dataSource(this);
        binding.typeRadioCoffee.setChecked(true);
        binding.saleDate.setFocusable(false);
        binding.saleDate.setInputType(InputType.TYPE_NULL);

        // set adaptors
        SetAdaptors();
        // set listeners
        SetListeners();
    }

    // default adaptors setting
    private void SetAdaptors() {
        binding.flavourSpinner.setAdapter(data_source.coffeeFlavourAdaptor);
        // city autocomplete widget
        binding.regionAutocomplete.setThreshold(2);
        binding.regionAutocomplete.setInputType(InputType.TYPE_NULL);
        binding.regionAutocomplete.setAdapter(data_source.GetCityAdapter());
        binding.storeSpinner.setAdapter(data_source.emptyAdaptor);
    }

    private void SetListeners() {
        binding.regionAutocomplete.setOnClickListener(this);
        binding.typeRadioGroup.setOnCheckedChangeListener(this);
        binding.additionalCheckboxMilk.setOnClickListener(this);
        binding.additionalCheckboxSugar.setOnClickListener(this);
        binding.sizeRadioGroup.setOnCheckedChangeListener(this);
        binding.regionAutocomplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String city_string = binding.regionAutocomplete.getText().toString();
                if (!Objects.isNull(data_source.cities_stores.get(city_string))) {
                    binding.storeSpinner.setAdapter(data_source.GetStoreAdapter(city_string));
                    // Set the first one as default store
                    if (!Objects.isNull(data_source.cities_stores.get(city_string)) && data_source.cities_stores.get(city_string).size() > 0) {
                        City = city_string;
                        Store = data_source.cities_stores.get(city_string).get(0);
                    }
                } else {
                    binding.storeSpinner.setAdapter(data_source.emptyAdaptor);
                    City = "";
                    Store = "";
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.flavourSpinner.setOnItemSelectedListener(this);
        binding.storeSpinner.setOnItemSelectedListener(this);
        binding.saleDate.setOnClickListener(this);
        binding.submitButton.setOnClickListener(this);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onClick(View v) {
        // datePicker's click event
        if (v.getId() == binding.saleDate.getId()) {
            Calendar cal = Calendar.getInstance();
            int saleDay = cal.get(Calendar.DAY_OF_MONTH);
            int saleMonth = cal.get(Calendar.MONTH);
            int saleYear = cal.get(Calendar.YEAR);
            datePicker = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> binding.saleDate.setText(String.format("%d/%d/%d", year, month + 1, dayOfMonth)), saleYear, saleMonth, saleDay);
            datePicker.show();
        }
        // Checkbox click
        else if (v.getId() == binding.additionalCheckboxMilk.getId()) {
            additional_mike = binding.additionalCheckboxMilk.isChecked();
        } else if (v.getId() == binding.additionalCheckboxSugar.getId()) {
            additional_sugar = binding.additionalCheckboxSugar.isChecked();
        }
        // Button's click action, validate and generate the list
        else if (v.getId() == binding.submitButton.getId()) {
            Validations.validate(binding, beverage_type, additional_mike, additional_sugar, beverage_size, adding_flavour, City, Store);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // when changing the beverage type, changing the flavouring adaptor
        if (group.getId() == binding.typeRadioGroup.getId()) {
            if (checkedId == binding.typeRadioCoffee.getId()) {
                binding.flavourSpinner.setAdapter(data_source.coffeeFlavourAdaptor);
                beverage_type = "coffee";
            } else if (checkedId == binding.typeRadioTea.getId()) {
                binding.flavourSpinner.setAdapter(data_source.teaFlavourAdaptor);
                beverage_type = "tea";
            }
        }
        // when tapping the radio button of
        else if (group.getId() == binding.sizeRadioGroup.getId()) {
            if (checkedId == binding.sizeRadioSmall.getId()) {
                beverage_size = "small";
            } else if (checkedId == binding.sizeRadioMedium.getId()) {
                beverage_size = "medium";
            } else if (checkedId == binding.sizeRadioLarge.getId()) {
                beverage_size = "large";
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Store is selecting
        if (!City.isEmpty() && parent.getId() == binding.storeSpinner.getId()) {
            Store = data_source.cities_stores.get(City).get(position);
        }
        // additional flavoring is selecting
        else if (parent.getId() == binding.flavourSpinner.getId()) {
            adding_flavour = "None";
            if (Objects.equals(beverage_type, "coffee")) {
                adding_flavour = data_source.coffeeFlavourAdaptor.getItem(position).toString();
            } else if (Objects.equals(beverage_type, "tea")) {
                adding_flavour = data_source.teaFlavourAdaptor.getItem(position).toString();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}