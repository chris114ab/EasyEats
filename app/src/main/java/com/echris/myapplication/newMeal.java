package com.echris.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class newMeal extends AppCompatActivity {
    TextView name,weight,price,calories;
    ArrayList<ingredient> ingList= new ArrayList<>();
    TableLayout ingTable;
    TableRow tr;

    //returns a new TableRow in the form required
    public TableRow newRow(int child){
        TextView tv = new TextView(this);
        tv.setText((child+1)+")");
        EditText nameEt = new EditText(this);
        nameEt.setHint("Name");
        EditText weightEt = new EditText(this);
        weightEt.setHint("Weight");
        EditText priceEt = new EditText(this);
        priceEt.setHint("Price");
        EditText caloriesEt = new EditText(this);
        caloriesEt.setHint("Calories");
        TableRow tr = new TableRow(this);
        tr.addView(tv);
        tr.addView(nameEt);
        tr.addView(weightEt);
        tr.addView(priceEt);
        tr.addView(caloriesEt);
        return tr;
    }

    //adds a new row to the table
    public void addRow(View view){
        TableLayout table = findViewById(com.echris.easyEats.R.id.ingredientTable);
        int i = table.getChildCount();
        table.addView(newRow(i));
    }

   //goes through 1 row and returns the entered ingredient
    public ingredient createIng(TableRow row){
        String[] list = new String[4];
        EditText nameEt = (EditText) row.getChildAt(1);
        list[0] = nameEt.getText().toString();
        EditText wieghtEt = (EditText) row.getChildAt(2);
        list[1] = wieghtEt.getText().toString();
        EditText priceEt = (EditText) row.getChildAt(3);
        list[2] = priceEt.getText().toString();
        EditText caloriesEt = (EditText) row.getChildAt(4);
        list[3] = caloriesEt.getText().toString();
        ingredient ing = new ingredient(list);
        Log.d("new ingredient", ing.toString());
        return ing;
    }

    //retrieves the existing meal record
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

    //collates all the rows into a complete meal and updates the record
    public void submitMeal(View v){
        mealRecord record = currentRecord();
        TableLayout table = findViewById(com.echris.easyEats.R.id.ingredientTable);
        EditText et = findViewById(com.echris.easyEats.R.id.mealName);
        String name = et.getText().toString();
        meal newMeal = new meal(ingList,name);

        for(int i=0;i<table.getChildCount();i++){
            TableRow row = (TableRow) table.getChildAt(i);
            ingredient ing = createIng(row);
            newMeal.addIng(ing);
        }
        record.add(newMeal);
        try {
            FileOutputStream fos = openFileOutput("record", 0);
            Log.d("FIleFound", "1");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            Log.d("FIleFound", "2");
            oos.writeObject(record);
            oos.close();
            fos.close();
            Log.d("FIleFound", "3");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Context context = getApplicationContext();
        Toast updateToast = Toast.makeText(context,"Meal Added!", Toast.LENGTH_SHORT);
        updateToast.show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.echris.easyEats.R.layout.activity_new_meal);
    }
}
