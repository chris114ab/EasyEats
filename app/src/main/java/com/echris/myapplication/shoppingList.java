package com.echris.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Hashtable;
import java.util.Set;

import static java.lang.String.valueOf;

public class shoppingList extends AppCompatActivity {



    public ingredient addIngs(ingredient ing1,ingredient ing2){
        int weight = Integer.parseInt(ing1.weight().trim())+Integer.parseInt(ing2.weight().trim());
        int price = Integer.parseInt(ing1.price().trim())+Integer.parseInt(ing2.price().trim());
        int calories = Integer.parseInt(ing1.calories().trim())+Integer.parseInt(ing2.calories().trim());
        ing1.details[1] = valueOf(weight);
        ing1.details[2] = valueOf(price);
        ing1.details[3] = valueOf(calories);
        return ing1;
    }


    public Hashtable<String, ingredient> totalIngs(mealRecord record){
        Hashtable<String, ingredient> shoppingList = new Hashtable<String, ingredient>();
        for(int i=0;i<record.list.size();i++){
            for(int j=0;j<record.list.get(i).length();j++){
                String name = record.list.get(i).get(j).name();
                if(shoppingList.get(name)!=null){
                    Log.d("duplicate found",shoppingList.get(name).name());
                    ingredient ing1 = record.list.get(i).get(j);
                    ingredient ing2 = shoppingList.get(name);
                    shoppingList.put(name, addIngs(ing1,ing2));
                }
                shoppingList.put(name,record.list.get(i).get(j));
            }
        }
        return shoppingList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.echris.easyEats.R.layout.activity_shopping_list);
        mealRecord list = (mealRecord)getIntent().getSerializableExtra("list");
        Log.d("the list",list.toString());
        TableLayout ingList = findViewById(com.echris.easyEats.R.id.ingList);
        Hashtable shopList = totalIngs(list);
        Set<String> shopSet = shopList.keySet();
        for(String key : shopSet) {
            TableRow row = new TableRow(this);
            ingredient ing = (ingredient) shopList.get(key);

            TextView nameEt = new TextView(this);
            nameEt.setText(ing.name());
            nameEt.setBackgroundResource(com.echris.easyEats.R.drawable.cell);
            nameEt.setTextSize(18);
            row.addView(nameEt);

            TextView weightEt = new TextView(this);
            weightEt.setText(ing.weight());
            weightEt.setBackgroundResource(com.echris.easyEats.R.drawable.cell);
            weightEt.setTextSize(18);
            row.addView(weightEt);

            TextView priceEt = new TextView(this);
            priceEt.setText(ing.price());
            priceEt.setBackgroundResource(com.echris.easyEats.R.drawable.cell);
            priceEt.setTextSize(18);
            row.addView(priceEt);

            TextView caloriesEt = new TextView(this);
            caloriesEt.setText(ing.calories());
            caloriesEt.setBackgroundResource(com.echris.easyEats.R.drawable.cell);
            caloriesEt.setTextSize(18);
            row.addView(caloriesEt);

            ingList.addView(row);
        }
    }
}
