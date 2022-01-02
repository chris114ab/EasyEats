package com.echris.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class editSelectedMeal extends AppCompatActivity {

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

    public TableLayout tableData(TableLayout table, meal selectedMeal){
        for(int i =0; i<selectedMeal.length();i++){
            table = rowTemplate(table,selectedMeal.get(i).name(),selectedMeal.get(i).weight(),selectedMeal.get(i).price(),selectedMeal.get(i).calories());
        }
        return table;
    }

    View.OnClickListener delete = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            View row = (View) view.getParent();
            TableLayout table = findViewById(com.echris.easyEats.R.id.mealTable);
            table.removeView(row);
        }
    };



    public TableLayout rowTemplate(TableLayout table, String name, String weight, String price, String calories){
        int i = table.getChildCount() -1;
        TableRow tr = new TableRow(this);

        EditText nameEt = new EditText(this);
        nameEt.setText(name);
        nameEt.setId(i*4);
        nameEt.setBackgroundResource(com.echris.easyEats.R.drawable.cell);
        tr.addView(nameEt);
        EditText weightEt = new EditText(this);
        weightEt.setText(weight);
        weightEt.setId(i*4+1);
        weightEt.setBackgroundResource(com.echris.easyEats.R.drawable.cell);
        tr.addView(weightEt);
        EditText priceEt = new EditText(this);
        priceEt.setText(price);
        priceEt.setId(i*4+2);
        priceEt.setBackgroundResource(com.echris.easyEats.R.drawable.cell);
        tr.addView(priceEt);
        EditText caloriesEt = new EditText(this);
        caloriesEt.setText(calories);
        caloriesEt.setId(i*4+3);
        caloriesEt.setBackgroundResource(com.echris.easyEats.R.drawable.cell);
        tr.addView(caloriesEt);
        ImageView delButton = new ImageView(this);
        delButton.setImageResource(com.echris.easyEats.R.drawable.small_bin);
        delButton.setBackgroundResource(com.echris.easyEats.R.drawable.delete_btn);
        delButton.setOnClickListener(delete);
        delButton.setPadding(5,5,5,5);
        tr.addView(delButton);
        table.addView(tr);
        return table;
    }


    public void confirmEdit(View view){
        int id = getIntent().getIntExtra("selectID",0);
        mealRecord record = currentRecord();
        TableLayout mealTable = findViewById(com.echris.easyEats.R.id.mealTable);
        meal selectedMeal = record.list.get(id);
        selectedMeal.clearIngs();
        for(int i=0;i<(mealTable.getChildCount()-1)*4;i++){

            TableRow row = (TableRow)mealTable.getChildAt((i/4)+1);
            EditText et = (EditText) row.getChildAt(i%4);

            try {
                selectedMeal.details.get(i / 4).details[i % 4] = et.getText().toString();
            }catch(Exception e){
                selectedMeal.placeholderIng( et.getText().toString());
            }

        }
        selectedMeal.printIngs();
        record.list.set(id,selectedMeal);
        updateRecord(record);
    }

    public void updateRecord(mealRecord record){
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
        Toast updateToast = Toast.makeText(context,"Meal Updated!", Toast.LENGTH_SHORT);
        updateToast.show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.echris.easyEats.R.layout.activity_edit_selected_meal);
        TableLayout mealTable = findViewById(com.echris.easyEats.R.id.mealTable);
        int id = getIntent().getIntExtra("selectID",0);
        mealRecord record = currentRecord();
        meal selectedMeal = record.list.get(id);
        TextView name = findViewById(com.echris.easyEats.R.id.mealName);
        Log.d("name",selectedMeal.name);
        name.setText(selectedMeal.name);
        name.setTextSize(24);
        name.setTextColor(getResources().getColor(com.echris.easyEats.R.color.black));
        mealTable = tableData(mealTable,selectedMeal);


        Button newRow = findViewById(com.echris.easyEats.R.id.addMealBtn);
        newRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TableLayout mealTable = findViewById(com.echris.easyEats.R.id.mealTable);
                mealTable = rowTemplate(mealTable,"","","","");
            }
        });


    }
}
