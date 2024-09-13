/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.Category;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import filemanager.IFileManager;

/**
 *
 * @author Asus
 */
public class CategoryList implements IItemManager<Category>, IFileManager {

    private List<Category> list;

    public CategoryList() {
        this.list = new ArrayList<>();
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
    public Category getOne(String id) {
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
        return this.list.add(item);
    }

    @Override
    public boolean update(String id, Category params) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String id) {
        Category itemDelete = this.getOne(id);
        return this.list.remove(itemDelete);
    }

    @Override
    public void saveFile() {
        PrintWriter fWriter = null;

        try {
            File file = new File("categories.txt");
            if (file.exists()) {
                fWriter = new PrintWriter("categories.txt");

                for (Category category : this.list) {
                    String tmp = category.getId() + "," + category.getName() + "\n";
                    fWriter.print(tmp);
                    fWriter.flush();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fWriter != null) {
                    fWriter.close();
                }
                System.out.println("Save data successfully");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }

    @Override
    public void loadFile() {
        FileReader fReader = null;
        BufferedReader bReader = null;

        try {
            File file = new File("categories.txt");

            if (file.exists()) {
                fReader = new FileReader("categories.txt");
                bReader = new BufferedReader(fReader);

                while (bReader.ready()) {
                    String dataLine = bReader.readLine();
                    String[] fields = dataLine.split(",");

                    if (fields.length == 2) {
                        Category obj = new Category(fields[0], fields[1]);
                        this.add(obj);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fReader != null) fReader.close();
                if (bReader != null) bReader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void printList () {
        if (this.list.isEmpty()) {
            System.out.println("Categories do not exist");
            return;
        }
        
        System.out.println("---- Category list ----");
        for (Category category : this.list) {
            System.out.println(category);
        }
    }

    @Override
    public boolean checkExistId(String id) {
        return this.getOne(id.toUpperCase()) != null;
    }
}
