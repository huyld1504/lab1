/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import filemanager.FileManager;
import filemanager.IFileManager;
import models.Category;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Asus
 */
public class CategoryList implements IItemManager<Category> {
    private static final String FILE_NAME = "categories.txt";

    private List<Category> list;

    public CategoryList() {
        this.loadFile();
    }

    public List<Category> getList() {
        return this.list;
    }
    

    @Override
    public List<Category> searchByName(String name) {
        List<Category> result = new ArrayList<>();

        for (Category category : this.list) {
            if (category.getName().equalsIgnoreCase(name)) {
                result.add(category);
            }
        }
        return result;
    }

    @Override
    public Category getItem(String id) {
        Category result = null;

        for (Category category : list) {
            if (category.getId().equalsIgnoreCase(id)) {
                result = category;
            }
        }
        return result;
    }

    @Override
    public boolean add(Category item) {
        if (item == null) {
            return false;
        }
        if (this.list == null) {
            this.list = new ArrayList<>();
        }
        return this.list.add(item);
    }

    @Override
    public boolean update(String id, Category params) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String id) {
        Category itemDelete = this.getItem(id);
        return this.list.remove(itemDelete);
    }
    
    public void loadFile() {
        IFileManager fileManager = new FileManager();
        
        try {
            List<String> dataLines = fileManager.loadFile(FILE_NAME);
            for (String dataLine : dataLines) {
                String[] fields = dataLine.split(",");
                if (fields.length == 2) {
                    String id = fields[0];
                    String name = fields[1];
                    this.add(new Category(id, name));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void printList () {
        if (this.list.isEmpty()) {
            System.out.println("Categories do not exist");
            return;
        }
        
        System.out.println("---- Category list ----");
        for (Category category : this.list) {
            System.out.println("ID: " + category.getId() + "  Name: " + category.getName());
        }
    }

    @Override
    public boolean checkExistId(String id) {
        return this.getItem(id.toUpperCase()) != null;
    }
}
