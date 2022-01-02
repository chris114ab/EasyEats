package com.echris.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.echris.easyEats.R.layout.activity_main);

    }
//functions taking user to each page
    public void goToShop(View view){
        Intent intent = new Intent(this, shop.class);
        startActivity(intent);
    }

    View.OnClickListener exitMealMenu = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            View newBtn = findViewById(com.echris.easyEats.R.id.newBtn);
            newBtn.setOnClickListener(null);
            View editBtn = findViewById(com.echris.easyEats.R.id.editBtn);
            editBtn.setOnClickListener(null);
            View container = findViewById(com.echris.easyEats.R.id.container);
            container.setVisibility(View.INVISIBLE);
            View mealView = findViewById(com.echris.easyEats.R.id.meal);
            mealView.setVisibility(View.VISIBLE);
            View panel = findViewById(com.echris.easyEats.R.id.panel);
            panel.setOnClickListener(null);
        }
    };

    public void mealMenu(View view){
        View container = findViewById(com.echris.easyEats.R.id.container);
        container.setVisibility(View.VISIBLE);
        View mealView = findViewById(com.echris.easyEats.R.id.meal);
        mealView.setVisibility(View.INVISIBLE);
        View editBtn = findViewById(com.echris.easyEats.R.id.editBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEditMeals();
            }
        });

        View newBtn = findViewById(com.echris.easyEats.R.id.newBtn);
        newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewMeal();
            }
        });
        View panel = findViewById(com.echris.easyEats.R.id.panel);
        panel.setOnClickListener(exitMealMenu);

    }

    public void goToNewMeal(){
        Intent intent = new Intent(this, newMeal.class);
        startActivity(intent);
    }

    public void goToEditMeals(){
        Intent intent = new Intent(this, editMeal.class);
        startActivity(intent);
    }



}
