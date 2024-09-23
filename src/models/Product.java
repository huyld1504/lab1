/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Asus
 */
public class Product extends Item implements Comparable<Product>{
    private int modelYear, listPrice;
    private Brand brand;
    private Category category;

    public Product(String id, String name) {
        super(id, name);
    }

    public Product(int modelYear, int listPrice, Brand brand, Category category, String id, String name) {
        super(id, name);
        this.modelYear = modelYear;
        this.listPrice = listPrice;
        this.brand = brand;
        this.category = category;
    }

    public int getModelYear() {
        return modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public int getListPrice() {
        return listPrice;
    }

    public void setListPrice(int listPrice) {
        this.listPrice = listPrice;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        String s = String.format("%s,%s,%s,%s,%d,%d", this.getId().toUpperCase(), this.getName(), this.brand.getId(), this.category.getId(), this.modelYear, this.listPrice);
        return s;
    }  
    
    public String printInfo() {
        String s = String.format("%s,%s,%d,%d,%s,%s", this.getId().toUpperCase(), this.getName(), this.modelYear, this.listPrice, this.getBrand().getName(), this.getCategory().getName());
        return s;
    }

    @Override
    public int compareTo(Product o) {
        if (this.getId().compareTo(o.getId()) > 0) return 1;
        else if (this.getId().compareTo(o.getId()) < 0) return -1;
        else return 0;
    }
}
