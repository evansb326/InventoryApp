package com.example.brian.inventoryapp;

//Model class that has getter and setter methods for the item, modelNumber, serialNumber, and id

import java.io.Serializable;

public class InventoryItem implements Serializable {
    private String item;
    private String modelNumber;
    private String serialNumber;
    private String id;


    InventoryItem(String item, String modelNumber, String serialNumber, String id){
        this.item = item;
        this.modelNumber = modelNumber;
        this.serialNumber = serialNumber;
        this.id = id;
    }

    String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getId() {
        return id;
    }

    public String toString() {
        return "ID Number: " + id + "\n" +
                "Item: " + item  + "\n" +
                "Model Number: " + modelNumber + "\n" +
                "Serial Number: " + serialNumber + "\n";
    }
}
