package com.echris.myapplication;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;


public class meal implements Serializable {

    ArrayList<ingredient> details;
    String name;

    //constructor given an arrayList of ingredients it will create the meal class
    public meal(ArrayList<ingredient> ingredients,String name){
        this.details = ingredients;
        this.name = name;
    }

    public void printIngs(){
        for(int i = 0; i<this.length();i++ ){
            ingredient currentIng = this.get(i);
            Log.d("print",currentIng.name() );
        }
    }

    public void clearIngs(){
        this.details = new ArrayList<>();
    }

    //add an ingredient to class
    public void addIng(ingredient add){
        this.details.add(add);
    }

    public void placeholderIng(String name){
        String[] placeholder = new String[4];
        placeholder[0] = name;
        this.details.add(new ingredient(placeholder));
    }

    public ingredient get(int index){
        return this.details.get(index);
    }

    public int length(){
        return this.details.size();
    }


    //remove an ingredient given an index catch error if index is not valid
    public void removeIng(int index){
        try {
            String removed = this.details.get(index).name();
            this.details.remove(index);
            Log.d("removed", removed);
        }catch (Exception e){
            Log.d("Failed","index not valid");
        }
    }


}
