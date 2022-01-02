package com.echris.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

public class mealRecord implements Serializable {
    ArrayList<meal> list;
    public mealRecord(){
        this.list = new ArrayList<meal>();
    }

    public void add(meal newMeal){
        this.list.add(newMeal);
    }

    public int length(){
        return this.list.size();
    }

    public void printMeals(){
        for(int i = 0; i<this.length();i++) {
            meal currentMeal = this.list.get(i);
            currentMeal.printIngs();
        }
    }

    public meal get(int index){
        return this.list.get(index);
    }

    //return an array of the meals in the mealRecord
    public ArrayList<meal> returnMeals(){
        return this.list;
    }

    public ArrayList<String> mealNames(){
        ArrayList<String> names = new ArrayList<>();
        for(int i=0;i<this.length();i++){
            names.add(this.get(i).name);
        }
        return names;
    }

    public meal search(String target){
        meal targetMeal= null;
        for( int i =0;i<this.length();i++){
            String name = this.list.get(i).name;
            if(name==target){
                targetMeal = this.list.get(i);
            }
        }
        return targetMeal;
    }


}
