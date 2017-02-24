package com.example.brian.inventoryapp;


import java.io.Serializable;

public class InventoryItem implements Serializable {
    String item;
    String modelNumber;
    String serialNumber;
    String id;

    public InventoryItem(){

    }

    public InventoryItem(String item, String modelNumber, String serialNumber, String id){
        this.item = item;
        this.modelNumber = modelNumber;
        this.serialNumber = serialNumber;
        this.id = id;
    }

    public String getItem() {
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

    public void setId(String id) {
        this.id = id;
    }

    public String toString() {
        return "Item: " + item  + "\n" +
                "Model Number: " + modelNumber + "\n" +
                "Serial Number: " + serialNumber + "\n" +
                "ID Number: " + id;
    }
}
