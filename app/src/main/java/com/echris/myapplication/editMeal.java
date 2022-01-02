package com.echris.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.FileInputStream;
import java.io.ObjectInputStream;


public class editMeal extends AppCompatActivity {

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

    View.OnClickListener editSelector = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id  = view.getId();
            goToEditSelectedMeal(id);
            //move to next view and pass the id as a reference to the selected meal
        }
    };

    public void goToEditSelectedMeal(int id){
        Intent intent = new Intent(this,editSelectedMeal.class);
        intent.putExtra("selectID",id);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.echris.easyEats.R.layout.activity_edit_meal);
        mealRecord record = currentRecord();
        TableLayout mealTable = findViewById(com.echris.easyEats.R.id.mealTable);
        mealTable.setColumnStretchable(0,true);
        mealTable.setColumnStretchable(1,true);

        int size = record.length();
        for(int i = 0; i<size;i++){
            TextView name = new TextView(this);
            name.setGravity(View.TEXT_ALIGNMENT_CENTER);
            FloatingActionButton editBtn = new FloatingActionButton(this);
            TableRow tr = new TableRow(this);
            tr.setPadding(20,20,20,20);
            name.setText(record.get(i).name);
            editBtn.setImageResource(com.echris.easyEats.R.drawable.edit_icon);
            editBtn.setId(i);
            editBtn.setOnClickListener(editSelector);
            tr.addView(name);
            tr.addView(editBtn);
            mealTable.addView(tr);
        }

    }
}
