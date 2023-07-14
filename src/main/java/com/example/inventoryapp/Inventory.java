package com.example.inventoryapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Inventory {
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();




    public void addPart(Part newPart) {
        allParts.add(newPart);
    }
    public void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    public Part lookupPart(int partId) {
        //Due to the constraints of only returning one Part object as specified in the UML and not a List,
        //partId is assumed to be equal to only one Part object

        //TODO: Return all matches of partial ID
        for(Part p: allParts) {
            if (Integer.toString(p.getId()).contains(Integer.toString(partId))) {
                return p;
            }
        }
        return null;
    }
    public Product lookupProduct(int productId) {
        for(Product p: allProducts) {
            if(p.getId() == productId) {
                return p;
            }
        }
        return null;
    }
    public ObservableList<Part> lookupPart(String partName) {
        if(partName.trim().isEmpty()) return null;
        ObservableList<Part> searchList = FXCollections.observableArrayList();
        for(Part p: getAllParts()) {
           if(p.getName().toLowerCase(Locale.ROOT).contains(partName)) {
               searchList.add(p);
           }
       }
       return searchList;
    }
    public ObservableList<Product> lookupProduct(String productName) {
        return allProducts.filtered(product -> product.getName().contains(productName));
    }
    public void updatePart(int index, Part selectedPart) {
        allParts.set(index,selectedPart);
    }
    public void updateProduct(int index, Product newProduct) {
        allProducts.set(index,newProduct);
    }
    public boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }
    public boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }
    public ObservableList<Part> getAllParts() { return allParts; }
    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }


}
