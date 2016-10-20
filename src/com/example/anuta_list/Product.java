package com.example.anuta_list;

public class Product {

    int _id;
    String _name;
    int _category;

    public Product(){
    }

    public Product(int id, String name, int __category){
        this._id = id;
        this._name = name;
        this._category = __category;
    }

    public Product(String name, String __category){
        this._name = name;
        this._category = _category;
    }

    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getName(){
        return this._name;
    }

    public void setName(String name){
        this._name = name;
    }

   

   

}
