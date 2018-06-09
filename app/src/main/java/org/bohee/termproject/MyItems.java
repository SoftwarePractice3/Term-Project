package org.bohee.termproject;

/**
 * Created by bohee on 2018-05-06.
 */

public class MyItems {
    private String storeName;
    private String foodType;
    private int table2;
    private int table4;
    private String address;

    public void setStoreName(String storeName){
        this.storeName=storeName;
    }

    public void setFoodType(String foodType){
        this.foodType=foodType;
    }

    public void setTable2(int table2){
        this.table2=table2;
    }

    public void setTable4(int table4){ this.table4=table4; }

    public void setAddress(String address){this.address = address;}

    public String getStoreName(){
        return this.storeName;
    }

    public String getFoodType(){
        return this.foodType;
    }

    public String getTable2(){
        return "2인석 : "+Integer.toString(this.table2)+"석";
    }

    public String getTable4(){
        return "4인석 : "+Integer.toString(this.table4)+"석";
    }

    public String getAddress() {return this.address;}

}
