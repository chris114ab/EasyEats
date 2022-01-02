package com.echris.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class shop extends AppCompatActivity {

    public mealRecord currentRecord(){
        try {
            FileInputStream fis = openFileInput("record");
            ObjectInputStream ois = new ObjectInputStream(fis);
            mealRecord record = (mealRecord)ois.readObject();
            ois.close();
            fis.close();
            return record;
        } catch (Exception e) {
            e.printStackTrace();
            return new mealRecord();
        }
    }

    public Spinner createMeal(ArrayList<String> meals){
        Spinner mealSelector = new Spinner(this);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, meals);
        mealSelector.setAdapter(spinnerArrayAdapter);
        return mealSelector;
    }

    public void sendMeal(mealRecord list){
        Intent intent = new Intent(this,shoppingList.class);
        intent.putExtra("list",list);
        startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.echris.easyEats.R.layout.activity_shop);
        final TableLayout shopTable = findViewById(com.echris.easyEats.R.id.shopTable);
        final mealRecord record = currentRecord();
        final ArrayList<String> names = record.mealNames();
        Button addMealBtn = findViewById(com.echris.easyEats.R.id.addMealBtn);
        final TextView tv = new TextView(this);
        final TableRow tr = new TableRow(this);
        final int i = 0;
        addMealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner spinner = createMeal(names);
                shopTable.addView(spinner);
            }
        });

        Button submitShop = findViewById(com.echris.easyEats.R.id.submitShop);
        submitShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mealRecord mealShop = new mealRecord();
                for(int i=0;i<shopTable.getChildCount();i++){
                    Spinner spinner = (Spinner)shopTable.getChildAt(i);
                    String mealName = spinner.getSelectedItem().toString();
                    meal newMeal = record.search(mealName);
                    mealShop.add(newMeal);
                }
            sendMeal(mealShop);
            }
        });
    }
}
