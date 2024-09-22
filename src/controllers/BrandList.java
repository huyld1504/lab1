/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.Brand;
import java.util.ArrayList;
import java.util.List;
import filemanager.FileManager;
import filemanager.IFileManager;

/**
 *
 * @author Asus
 */
public class BrandList implements IItemManager<Brand> {

    private static final String FILE_NAME = "brands.txt";

    private List<Brand> brands;

    public BrandList() {
        this.loadFile();
    }

    public List<Brand> getList() {
        return this.brands;
    }

    @Override
    public List<Brand> searchByName(String name) {
        List<Brand> result = new ArrayList<>();

        for (Brand brand : this.brands) {
            if (brand.getName().equalsIgnoreCase(name)) {
                result.add(brand);
            }
        }
        return result;
    }

    @Override
    public Brand getItem(String id) {
        Brand result = null;
        for (Brand brand : this.brands) {
            if (brand.getId().equals(id)) {
                result = brand;
            }
        }
        return result;
    }

    @Override
    public boolean add(Brand item) {
        if (item == null) {
            return false;
        }
        if (this.brands == null) {
            this.brands = new ArrayList<>();
        }
        return this.brands.add(item);
    }

    @Override
    public boolean update(String id, Brand params) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        Brand itemDelete = this.getItem(id);
        if (itemDelete == null) {
            return false;
        }
        return this.brands.remove(itemDelete);
    }

    private void loadFile() {
        IFileManager fileManager = new FileManager();

        try {
            List<String> dataLines = fileManager.loadFile(FILE_NAME);
            
            for (String dataLine : dataLines) {
                String[] fields = dataLine.split(",");
                if (fields.length == 3) {
                    String id = fields[0];
                    String name = fields[1];
                    String address = fields[2];
                    this.add(new Brand(id, name, address));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void printList() {
        if (this.brands.isEmpty()) {
            System.out.println("Brands do not exists");
            return;
        }

        System.out.println("---- List brands ----");
        for (Brand brand : brands) {
            System.out.println("ID: " + brand.getId() + "  Name: " + brand.getName() + "  Address: " + brand.getAddress());
        }
    }

    @Override
    public boolean checkExistId(String id) {
        return this.getItem(id.toUpperCase()) != null;
    }

}
