package com.example.siyuassignment1.models;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class dataSource {
    private final Context context;
    public HashMap<String, ArrayList<String>> cities_stores = new HashMap<>();
    public ArrayAdapter coffeeFlavourAdaptor;
    public ArrayAdapter teaFlavourAdaptor;
    public ArrayAdapter emptyAdaptor;

    public dataSource(Context context) {
        AddCityStores("Waterloo", new String[]{"65 University Ave E", "415 King St", "585 Weber St"});
        AddCityStores("London", new String[]{"616 Wharncliffe Rd", "1885 Huron St", "670 Wonderland Road", "1181 Highbury Ave"});
        AddCityStores("Milton", new String[]{"900 Steeles Ave", "80 Market Dr", "820 Main St"});
        AddCityStores("Mississauga", new String[]{"144 Dundas St", "30 Eglinton Ave", "6075 Creditview Rd"});
        // cities_stores
        this.context = context;
        emptyAdaptor = GenerateAdapter(new String[]{"None"});
        // flavouring source
        coffeeFlavourAdaptor = GenerateAdapter(new String[]{"None", "Pumpkin Spice", "Chocolate"});
        teaFlavourAdaptor = GenerateAdapter(new String[]{"None", "Lemon", "Ginger"});
    }

    public void AddCityStores(String city, String[] stores) {
        cities_stores.put(city, new ArrayList<String>(Arrays.asList(stores)));
    }

    public ArrayAdapter GetCityAdapter() {
        String[] cities = new String[cities_stores.size()];
        cities_stores.keySet().toArray(cities);
        return GenerateAdapter(cities);
    }

    public ArrayAdapter GetStoreAdapter(String city) {
        String[] stores = new String[cities_stores.get(city).size()];
        cities_stores.get(city).toArray(stores);
        return GenerateAdapter(stores);
    }

    private ArrayAdapter GenerateAdapter(String[] source) {
        return new ArrayAdapter(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, source);
    }
}