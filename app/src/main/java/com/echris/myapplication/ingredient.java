package com.echris.myapplication;

import java.io.Serializable;

public class ingredient implements Serializable {

    String[] details;

    //constructor given all the properties, create a new ingredient
    public ingredient(String[] list) {
        this.details = list;
    }

    //return property of ingredient
    public String name(){
        return this.details[0];
    }
    public String weight(){
        return this.details[1];
    }
    public String price(){
        return this.details[2];
    }
    public String calories(){
        return this.details[3];
    }

}
